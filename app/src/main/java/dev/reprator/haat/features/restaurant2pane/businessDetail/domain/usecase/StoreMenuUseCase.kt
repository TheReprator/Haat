package dev.reprator.haat.features.restaurant2pane.businessDetail.domain.usecase

import dagger.hilt.android.scopes.ViewModelScoped
import dev.reprator.haat.features.restaurant2pane.businessDetail.domain.repository.StoreMenuRepository
import javax.inject.Inject

@ViewModelScoped
class StoreMenuUseCase @Inject constructor(private val repository: StoreMenuRepository) {
    suspend operator fun invoke(storeId: String) = repository.fetchStoreInfo(storeId)
}