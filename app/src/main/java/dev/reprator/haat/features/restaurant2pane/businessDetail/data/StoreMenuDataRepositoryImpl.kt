package dev.reprator.haat.features.restaurant2pane.businessDetail.data

import dev.reprator.haat.features.restaurant2pane.businessDetail.domain.modal.ModelUIStoreContainer
import dev.reprator.haat.features.restaurant2pane.businessDetail.domain.repository.StoreMenuRepository
import dev.reprator.haat.util.AppResult
import javax.inject.Inject

class StoreMenuDataRepositoryImpl @Inject constructor(private val menuDataRepository: StoreMenuDataRepository):
    StoreMenuRepository {

    override suspend fun fetchStoreInfo(storeId: String): AppResult<ModelUIStoreContainer> {
        return menuDataRepository.fetchHomeMenu(storeId)
    }
}