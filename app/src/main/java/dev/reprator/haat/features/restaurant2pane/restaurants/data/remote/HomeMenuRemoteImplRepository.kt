package dev.reprator.haat.features.restaurant2pane.restaurants.data.remote

import com.fasterxml.jackson.databind.ObjectMapper
import dev.reprator.haat.di.ApiService
import dev.reprator.haat.features.restaurant2pane.restaurants.data.HomeMenuDataRepository
import dev.reprator.haat.features.restaurant2pane.restaurants.data.remote.modal.EntityMenuContainer
import dev.reprator.haat.features.restaurant2pane.restaurants.domain.modal.ModelUIMenuContainer
import dev.reprator.haat.util.AppError
import dev.reprator.haat.util.AppResult
import dev.reprator.haat.util.AppSuccess
import dev.reprator.haat.util.Mapper
import dev.reprator.haat.util.safeApiCall
import javax.inject.Inject

class HomeMenuRemoteImplRepository @Inject constructor(private val apiService: ApiService,
    private val objectMapper: ObjectMapper,
    private val mapper: @JvmSuppressWildcards Mapper<EntityMenuContainer?, ModelUIMenuContainer>
): HomeMenuDataRepository {

    override suspend fun fetchHomeMenu(): AppResult<ModelUIMenuContainer> {
        val response = safeApiCall(objectMapper) {
            apiService.menuData()
        }
        val responseMapped = when(response) {
            is AppSuccess -> {
                AppSuccess(mapper.map(response.data))
            }
            is AppError -> {
                response
            }
        }
        return responseMapped
    }
}