package dev.reprator.haat.features.restaurant2pane.restaurants.presentation

import dev.reprator.haat.util.base.mvi.Reducer
import javax.inject.Inject

class HomeMenuScreenReducer  @Inject constructor():
    Reducer<HomeMenuState, HomeMenuAction, HomeMenuEffect> {

    override fun reduce(
        previousState: HomeMenuState,
        action: HomeMenuAction
    ): Pair<HomeMenuState, HomeMenuEffect?> {
        return when (action) {

            is HomeMenuAction.FetchHomeMenu,HomeMenuAction.RetryHomeMenu -> {
                previousState.copy(
                    isLoading = true,
                    errorMessage = "",
                    isError = false,
                ) to null
            }

            is HomeMenuAction.UpdateHomeMenu -> {
                previousState.copy(
                    isError = false,
                    isLoading = false,
                    errorMessage = "",
                    menuData = action.menu
                ) to null
            }

            is HomeMenuAction.UserHomeMenuError -> {
                previousState.copy(
                    isLoading = false,
                    isError = true,
                    errorMessage = action.message,
                ) to null
            }

            is HomeMenuAction.OpenStore -> {
                previousState to null
            }
        }
    }
}