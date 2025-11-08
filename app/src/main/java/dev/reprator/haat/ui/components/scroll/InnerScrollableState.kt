package dev.reprator.haat.ui.components.scroll

import androidx.compose.foundation.gestures.ScrollableState
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.grid.LazyGridState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.compose.LifecycleEventEffect

class InnerScrollableState {
    private val _gridStates: MutableList<ScrollableState> = mutableListOf()
    val gridStates: List<ScrollableState> = _gridStates

    fun getOrCreateStateForGrid(carouselIndex: Int): LazyGridState {
        return if ((0 <= carouselIndex) && (_gridStates.size > carouselIndex)) {
            _gridStates[carouselIndex] as LazyGridState
        } else {
            LazyGridState().also {
                _gridStates.add(it)
            }
        }
    }

    fun clearState() {
        _gridStates.clear()
    }
}


data class SingleNestedScroll(val innerScrollableState: InnerScrollableState,
    val nestedScrollConnection: NestedVerticalGridsScrollConnection, val viewportHeight: Dp
)

@Composable
fun rememberSingleNestedScroll(state: LazyListState): SingleNestedScroll {

    val innerScrollableState = remember { InnerScrollableState() }

    LifecycleEventEffect(Lifecycle.Event.ON_STOP) {
        innerScrollableState.clearState()
    }

    val viewportHeight = with(LocalDensity.current) {
        (state.layoutInfo.viewportSize.height).dp
    }

    val nestedScrollConnection = remember {
        NestedVerticalGridsScrollConnection(
            state,
            innerScrollableState.gridStates,
        )
    }

    return remember(viewportHeight) {
        SingleNestedScroll(innerScrollableState, nestedScrollConnection, viewportHeight)
    }
}