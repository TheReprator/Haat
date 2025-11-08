package dev.reprator.haat.features.restaurant2pane.businessDetail.domain.repository

import dev.reprator.haat.features.restaurant2pane.businessDetail.domain.modal.ModelUIStoreContainer
import dev.reprator.haat.util.AppResult

interface StoreMenuRepository {
    suspend fun fetchStoreInfo(storeId: String): AppResult<ModelUIStoreContainer>
}