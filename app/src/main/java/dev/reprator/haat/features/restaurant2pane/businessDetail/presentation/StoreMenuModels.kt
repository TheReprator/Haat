package dev.reprator.haat.features.restaurant2pane.businessDetail.presentation


import dev.reprator.haat.features.restaurant2pane.businessDetail.domain.modal.ModelUIStoreContainer
import dev.reprator.haat.features.restaurant2pane.businessDetail.domain.modal.ModelUIStoreProductItem
import dev.reprator.haat.util.base.mvi.SideEffect
import dev.reprator.haat.util.base.mvi.UiAction
import dev.reprator.haat.util.base.mvi.UiState

enum class ProductAction{
    Addition, Substraction
}

sealed interface StoreInfoAction : UiAction {
    object FetchStoreData : StoreInfoAction
    object RetryStoreData : StoreInfoAction
    data class UpdateStoreInfo(val menu: ModelUIStoreContainer) : StoreInfoAction
    data class StoreInfoError(val message: String) : StoreInfoAction
    data class AddOperationToProduct(val action: ProductAction, val productItem: ModelUIStoreProductItem) : StoreInfoAction
    object NavigateToBack : StoreInfoAction
}


sealed interface StoreInfoEffect : SideEffect {
    data class ShowError(val message: String) : StoreInfoEffect
}


data class StoreInfoState(
    val isLoading: Boolean,
    val isError: Boolean,
    val errorMessage: String,
    val storeData: ModelUIStoreContainer
) : UiState {
    companion object {
        fun initial(): StoreInfoState {
            return StoreInfoState(
                isLoading = false,
                isError = false,
                errorMessage = "",
                storeData = ModelUIStoreContainer.initial
            )
        }
    }
}