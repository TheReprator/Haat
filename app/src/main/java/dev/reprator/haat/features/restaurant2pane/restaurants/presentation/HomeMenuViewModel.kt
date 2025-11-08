package dev.reprator.haat.features.restaurant2pane.restaurants.presentation

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import dev.reprator.haat.util.AppCoroutineDispatchers
import dev.reprator.haat.util.base.mvi.MVI
import dev.reprator.haat.util.base.mvi.Middleware
import dev.reprator.haat.util.base.mvi.Reducer
import dev.reprator.haat.util.base.mvi.mvi
import javax.inject.Inject

@HiltViewModel
class HomeMenuViewModel @Inject constructor(
    private val dispatchers: AppCoroutineDispatchers,
    private val middleWareList: Set<@JvmSuppressWildcards Middleware<HomeMenuState, HomeMenuAction, HomeMenuEffect>>,
    private val reducer: Reducer<HomeMenuState, HomeMenuAction, HomeMenuEffect>,
) : ViewModel(), MVI<HomeMenuState, HomeMenuAction, HomeMenuEffect> by mvi(
    dispatchers,
    reducer,
    middleWareList,
    HomeMenuState.initial()
) {
    override fun onCleared() {
        middleWareList.forEach {
            it.close()
        }
        super.onCleared()
    }
}