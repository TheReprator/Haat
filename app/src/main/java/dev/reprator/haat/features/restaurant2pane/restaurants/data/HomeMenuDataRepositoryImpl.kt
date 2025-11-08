package dev.reprator.haat.features.restaurant2pane.restaurants.data

import dev.reprator.haat.features.restaurant2pane.restaurants.domain.modal.ModelUIMenuContainer
import dev.reprator.haat.features.restaurant2pane.restaurants.domain.repository.HomeMenuRepository
import dev.reprator.haat.util.AppResult
import javax.inject.Inject

class HomeMenuDataRepositoryImpl @Inject constructor(private val menuDataRepository: HomeMenuDataRepository): HomeMenuRepository {
    override suspend fun fetchHomeMenu(): AppResult<ModelUIMenuContainer> {
        return menuDataRepository.fetchHomeMenu()
    }
}