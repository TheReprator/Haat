package dev.reprator.haat.features.restaurant2pane.restaurants.domain.repository

import dev.reprator.haat.features.restaurant2pane.restaurants.domain.modal.ModelUIMenuContainer
import dev.reprator.haat.util.AppResult

interface HomeMenuRepository {
    suspend fun fetchHomeMenu(): AppResult<ModelUIMenuContainer>
}