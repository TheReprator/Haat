package dev.reprator.haat.features.restaurant2pane.businessDetail.data.remote

import com.fasterxml.jackson.databind.ObjectMapper
import dev.reprator.haat.di.ApiService
import dev.reprator.haat.features.restaurant2pane.businessDetail.data.StoreMenuDataRepository
import dev.reprator.haat.features.restaurant2pane.businessDetail.data.remote.modal.EntityStoreInfoContainer
import dev.reprator.haat.features.restaurant2pane.businessDetail.data.remote.modal.EntityStoreMenuContainer
import dev.reprator.haat.features.restaurant2pane.businessDetail.domain.modal.ModelUIStoreContainer
import dev.reprator.haat.util.AppError
import dev.reprator.haat.util.AppResult
import dev.reprator.haat.util.AppSuccess
import dev.reprator.haat.util.Mapper
import dev.reprator.haat.util.safeApiCall
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.coroutineScope
import javax.inject.Inject

class StoreMenuRemoteImplRepository @Inject constructor(
    private val apiService: ApiService,
    private val objectMapper: ObjectMapper,
    private val mapper: @JvmSuppressWildcards Mapper<Pair<EntityStoreMenuContainer,
            EntityStoreInfoContainer>?, ModelUIStoreContainer>

) : StoreMenuDataRepository {


    override suspend fun fetchHomeMenu(storeId: String): AppResult<ModelUIStoreContainer> {
        return mergeAllCallTogether(storeId)
    }

    suspend fun mergeAllCallTogether(storeId: String): AppResult<ModelUIStoreContainer> =
        coroutineScope {
            val storeInfo = async { fetchVenueInfo(storeId) }
            val storeMenu = async { fetchVenueMenu(storeId) }

            val (info, menu) = awaitAll(storeInfo, storeMenu)

            return@coroutineScope when {

                info is AppSuccess && menu is AppSuccess -> {
                    val response = Pair((menu.data as EntityStoreMenuContainer),(info.data as EntityStoreInfoContainer))
                    val mappedData = mapper.map(response)

                    AppSuccess(mappedData)
                }
                else -> {
                    val infoError = if(info is AppError) info.message.orEmpty() else ""
                    val menuError = if(menu is AppError) menu.message.orEmpty() else ""
                    AppError(message = infoError.ifEmpty { menuError })
                }
            }
        }


    suspend fun fetchVenueInfo(storeId: String): AppResult<EntityStoreInfoContainer> {
        val response = safeApiCall(objectMapper) {
            apiService.venueInfo(storeId)
        }
        return response
    }

    suspend fun fetchVenueMenu(storeId: String): AppResult<EntityStoreMenuContainer> {
        val response = safeApiCall(objectMapper) {
            apiService.venueMenu("4012")
        }
        return response
    }
}