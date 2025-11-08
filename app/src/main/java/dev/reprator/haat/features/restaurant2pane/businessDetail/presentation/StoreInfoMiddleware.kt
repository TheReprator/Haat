package dev.reprator.haat.features.restaurant2pane.businessDetail.presentation


import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import dev.reprator.haat.features.restaurant2pane.businessDetail.domain.modal.ModelUIStoreContainer
import dev.reprator.haat.features.restaurant2pane.businessDetail.domain.modal.ModelUIStoreItemType
import dev.reprator.haat.features.restaurant2pane.businessDetail.domain.modal.ModelUIStoreProductContainer
import dev.reprator.haat.features.restaurant2pane.businessDetail.domain.modal.ModelUIStoreProductItem
import dev.reprator.haat.features.restaurant2pane.businessDetail.domain.modal.ModelUiOrderInfoContainer
import dev.reprator.haat.features.restaurant2pane.businessDetail.domain.modal.ModelUiOrderInfoItem
import dev.reprator.haat.features.restaurant2pane.businessDetail.domain.modal.StoreListCategory
import dev.reprator.haat.features.restaurant2pane.businessDetail.domain.usecase.StoreMenuUseCase
import dev.reprator.haat.util.AppCoroutineDispatchers
import dev.reprator.haat.util.AppError
import dev.reprator.haat.util.AppSuccess
import dev.reprator.haat.util.base.mvi.ActionDispatcher
import dev.reprator.haat.util.base.mvi.Middleware
import kotlinx.coroutines.CompletableDeferred
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import kotlin.coroutines.CoroutineContext

class StoreInfoMiddleware @AssistedInject constructor(
    @Assisted val storeId: String,
    private val storeMenuUseCase: StoreMenuUseCase,
    private val dispatchers: AppCoroutineDispatchers
) : Middleware<StoreInfoState, StoreInfoAction, StoreInfoEffect> {

    @AssistedFactory
    fun interface Factory {
        operator fun invoke(storeId: String): StoreInfoMiddleware
    }

    private val coroutineExceptionHandler = CoroutineExceptionHandler { _, throwable ->

    }

    override val coroutineContext: CoroutineContext =
        SupervisorJob() + Dispatchers.Main.immediate + coroutineExceptionHandler

    private val mviDispatcher = CompletableDeferred<(StoreInfoAction) -> Unit>()

    override fun attach(dispatcher: ActionDispatcher<StoreInfoAction>) {
        mviDispatcher.complete(dispatcher)
    }

    override fun onAction(
        action: StoreInfoAction,
        state: StoreInfoState
    ) {
        when (action) {
            is StoreInfoAction.RetryStoreData, StoreInfoAction.FetchStoreData -> {
                handleStoreInfoFetch()
            }

            is StoreInfoAction.AddOperationToProduct -> {
                addOrRemoveProduct(state,action)
            }

            else -> {}
        }
    }

    private fun addOrRemoveProduct( state: StoreInfoState, productOperation: StoreInfoAction.AddOperationToProduct) {
        launch(dispatchers.io) {
            val item = productOperation.productItem

            val itemQuantity = item.quantityWeight.currentQuantity
            val(quantity, isAdded) = when(productOperation.action) {
                ProductAction.Addition -> {
                    if(item.isAdded) {
                        (itemQuantity + 1) to true
                    }
                    else {
                        itemQuantity to true
                    }
                }
                ProductAction.Substraction -> {
                    if(1 == item.quantityWeight.currentQuantity) {
                        1 to false
                    } else {
                        (itemQuantity - 1) to true
                    }
                }
            }

            val updatedQuantity = item.quantityWeight.copy(currentQuantity = quantity)
            val updatedProduct = item.copy(quantityWeight = updatedQuantity, isAdded = isAdded)

            val updateCategoryList: List<StoreListCategory>? = updateMainCategory(state, updatedProduct)
            val orderInfoSessionItem: ModelUiOrderInfoContainer? = updateOrderInfo(state, updatedProduct)

            val updateStoreContainer = when {
                ((null != updateCategoryList) && (null != orderInfoSessionItem)) -> {
                    state.storeData.copy(storeCategoryList = updateCategoryList, orderInfo = orderInfoSessionItem)
                }

                (null != updateCategoryList) -> {
                    state.storeData.copy(storeCategoryList = updateCategoryList)
                }

                (null != orderInfoSessionItem) -> {
                    state.storeData.copy(orderInfo = orderInfoSessionItem)
                }

                else -> {
                    return@launch
                }
            }

            withContext(dispatchers.main) {
                mviDispatcher.await()(StoreInfoAction.UpdateStoreInfo(updateStoreContainer))
            }
        }
    }

    private fun updateOrderInfo(state: StoreInfoState, productItem: ModelUIStoreProductItem): ModelUiOrderInfoContainer? {
        val sessionOrder: ModelUiOrderInfoItem? = state.storeData.orderInfo.storeOrders[storeId]
        val updatedStoreOrders = state.storeData.orderInfo.storeOrders.toMutableMap()

        val updatedModelUiOrderInfoItem = if(null == sessionOrder) {
            val sessionOrderItem = ModelUiOrderInfoItem(listOf(productItem))
            sessionOrderItem
        } else {
            val productIndex = sessionOrder.itemAddedList.indexOfFirst {
                it.id == productItem.id
            }

            val updatedProductList = sessionOrder.itemAddedList.toMutableList()

            if(-1 >= productIndex) {
                updatedProductList.add(productItem)
            } else {
                if(productItem.isAdded) {
                    updatedProductList[productIndex] = productItem
                } else {
                    updatedProductList.removeAt(productIndex)
                }
            }

            if(updatedProductList.isEmpty()) {
                updatedStoreOrders.remove(storeId)
                return state.storeData.orderInfo.copy(storeOrders = updatedStoreOrders)
            }

            val orderInfoSessionItem = updatedStoreOrders[storeId]!!.copy(itemAddedList = updatedProductList)
            orderInfoSessionItem
        }

        updatedStoreOrders[storeId] = updatedModelUiOrderInfoItem
        val updatedOrderInfo = state.storeData.orderInfo.copy(storeOrders = updatedStoreOrders)
        return updatedOrderInfo
    }

    private fun updateMainCategory(state: StoreInfoState, productItem: ModelUIStoreProductItem): List<StoreListCategory>? {
           val categoryIndex = state.storeData.storeCategoryList.indexOfFirst {
               it.id == productItem.categoryId
           }
           if(-1 >= categoryIndex) {
               return null
           }

           val productContainer = state.storeData.storeCategoryList[categoryIndex] as ModelUIStoreProductContainer
           val productIndex = productContainer.productList.indexOfFirst { it.id == productItem.id }
           if(-1 >= productIndex) {
               return null
           }

           val updatedProductList = productContainer.productList.toMutableList()
           updatedProductList[productIndex] = productItem

           val updatedProductContainer = productContainer.copy(productList = updatedProductList)

           val updateCategoryList = state.storeData.storeCategoryList.toMutableList()
           updateCategoryList[categoryIndex] = updatedProductContainer

           return updateCategoryList
    }

    private fun handleStoreInfoFetch() {
        launch(dispatchers.io) {
            val result = storeMenuUseCase(storeId)

            withContext(dispatchers.main) {
                when (result) {
                    is AppSuccess<ModelUIStoreContainer> -> {
                        mviDispatcher.await()(StoreInfoAction.UpdateStoreInfo(result.data))
                    }

                    is AppError -> {
                        mviDispatcher.await()(
                            StoreInfoAction.StoreInfoError(
                                result.message ?: result.throwable?.message ?: ""
                            )
                        )
                    }
                }
            }
        }
    }
}