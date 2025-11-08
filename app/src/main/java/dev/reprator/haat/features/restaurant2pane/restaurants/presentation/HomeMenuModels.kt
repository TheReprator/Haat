package dev.reprator.haat.features.restaurant2pane.restaurants.presentation

import dev.reprator.haat.features.restaurant2pane.restaurants.domain.modal.ModelUIMenuContainer
import dev.reprator.haat.util.base.mvi.SideEffect
import dev.reprator.haat.util.base.mvi.UiAction
import dev.reprator.haat.util.base.mvi.UiState

sealed interface HomeMenuAction : UiAction {
    object FetchHomeMenu : HomeMenuAction
    object RetryHomeMenu : HomeMenuAction
    data class UpdateHomeMenu(val menu: ModelUIMenuContainer) : HomeMenuAction
    data class UserHomeMenuError(val message: String) : HomeMenuAction
    data class OpenStore(val storeId: String) : HomeMenuAction
}

sealed interface HomeMenuEffect : SideEffect {
    data class ShowError(val message: String) : HomeMenuEffect
}

data class HomeMenuState(
    val isLoading: Boolean,
    val isError: Boolean,
    val errorMessage: String,
    val menuData: ModelUIMenuContainer
) : UiState {
    companion object {
        fun initial(): HomeMenuState {
            return HomeMenuState(
                isLoading = false,
                isError = false,
                errorMessage = "",
                menuData = ModelUIMenuContainer.initial
            )
        }
    }
}