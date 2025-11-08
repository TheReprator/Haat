package dev.reprator.haat.features.restaurant2pane.restaurants.data

import dev.reprator.haat.features.restaurant2pane.restaurants.domain.modal.ModelUIMenuContainer
import dev.reprator.haat.util.AppResult


interface HomeMenuDataRepository {
    suspend fun fetchHomeMenu(): AppResult<ModelUIMenuContainer>
}