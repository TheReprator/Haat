package dev.reprator.haat.features.restaurant2pane.businessDetail.data

import dev.reprator.haat.features.restaurant2pane.businessDetail.domain.modal.ModelUIStoreContainer
import dev.reprator.haat.features.restaurant2pane.restaurants.domain.modal.ModelUIMenuContainer
import dev.reprator.haat.util.AppResult


interface StoreMenuDataRepository {
    suspend fun fetchHomeMenu(storeId: String): AppResult<ModelUIStoreContainer>
}