package dev.reprator.haat.features.restaurant2pane.businessDetail.presentation

import androidx.lifecycle.ViewModel
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import dagger.hilt.android.lifecycle.HiltViewModel
import dev.reprator.haat.util.AppCoroutineDispatchers
import dev.reprator.haat.util.base.mvi.MVI
import dev.reprator.haat.util.base.mvi.Middleware
import dev.reprator.haat.util.base.mvi.Reducer
import dev.reprator.haat.util.base.mvi.mvi
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.StateFlow

@HiltViewModel(assistedFactory = StoreInfoViewModel.Factory::class)
class StoreInfoViewModel @AssistedInject constructor(
    @Assisted val storeId: String,
    middleWareFactory: StoreInfoMiddleware.Factory,
    dispatchers: AppCoroutineDispatchers,
    reducer: Reducer<StoreInfoState, StoreInfoAction, StoreInfoEffect>,
) : ViewModel(), MVI<StoreInfoState, StoreInfoAction, StoreInfoEffect> {

    private val middleWareList: Set<Middleware<StoreInfoState, StoreInfoAction, StoreInfoEffect>> =
        setOf(middleWareFactory(storeId))

    private val delegate = mvi(dispatchers, reducer,
        middleWareList, StoreInfoState.initial())


    override val uiState: StateFlow<StoreInfoState> = delegate.uiState
    override val sideEffect: Flow<StoreInfoEffect> = delegate.sideEffect
    override val currentState: StoreInfoState = delegate.currentState

    override fun onAction(uiAction: StoreInfoAction) = delegate.onAction(uiAction)
    override fun updateUiState(block: StoreInfoState.() -> StoreInfoState) = delegate.updateUiState(block)
    override fun updateUiState(newUiState: StoreInfoState) = delegate.updateUiState(newUiState)

    override fun CoroutineScope.emitSideEffect(effect: StoreInfoEffect) {
        with(delegate) {
            this@emitSideEffect.emitSideEffect(effect)
        }
    }

    @AssistedFactory
    interface Factory {
        fun create(
            storeId: String,
        ): StoreInfoViewModel
    }

    override fun onCleared() {
        middleWareList.forEach {
            it.close()
        }
        super.onCleared()
    }
}