package dev.reprator.haat.features.restaurant2pane.businessDetail.presentation

import dev.reprator.haat.util.base.mvi.Reducer
import javax.inject.Inject

class StoreInfoScreenReducer  @Inject constructor():
    Reducer<StoreInfoState, StoreInfoAction, StoreInfoEffect> {

    override fun reduce(
        previousState: StoreInfoState,
        action: StoreInfoAction
    ): Pair<StoreInfoState, StoreInfoEffect?> {
        return when (action) {

            is StoreInfoAction.FetchStoreData, StoreInfoAction.RetryStoreData -> {
                previousState.copy(
                    isLoading = true,
                    errorMessage = "",
                    isError = false,
                ) to null
            }

            is StoreInfoAction.UpdateStoreInfo -> {
                previousState.copy(
                    isError = false,
                    isLoading = false,
                    errorMessage = "",
                    storeData = action.menu
                ) to null
            }

            is StoreInfoAction.StoreInfoError -> {
                previousState.copy(
                    isLoading = false,
                    isError = true,
                    errorMessage = action.message,
                ) to null
            }

            else -> {
                previousState to null
            }
        }
    }
}