package dev.reprator.haat.features.restaurant2pane.restaurants.domain.usecase

import dev.reprator.haat.features.restaurant2pane.restaurants.domain.repository.HomeMenuRepository
import javax.inject.Inject

class HomeMenuUseCase @Inject constructor(private val repository: HomeMenuRepository) {
    suspend operator fun invoke() = repository.fetchHomeMenu()
}