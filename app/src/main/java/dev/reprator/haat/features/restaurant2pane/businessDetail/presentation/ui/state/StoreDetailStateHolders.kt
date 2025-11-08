package dev.reprator.haat.features.restaurant2pane.businessDetail.presentation.ui.state

import androidx.compose.animation.core.animate
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.input.nestedscroll.NestedScrollConnection
import androidx.compose.ui.input.nestedscroll.NestedScrollSource
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.lerp
import dev.reprator.haat.features.restaurant2pane.businessDetail.domain.modal.ModelUIStoreContainer
import dev.reprator.haat.features.restaurant2pane.businessDetail.domain.modal.StoreCategory
import dev.reprator.haat.features.restaurant2pane.businessDetail.presentation.ui.HEADER_TOOLBAR_SEARCH_ROW_HEIGHT
import dev.reprator.haat.features.restaurant2pane.businessDetail.presentation.ui.LIST_PADDING_TOP
import dev.reprator.haat.features.restaurant2pane.businessDetail.presentation.ui.TOOLBAR_EXPANDED_HEIGHT
import dev.reprator.haat.ui.components.app.density
import dev.reprator.haat.ui.components.app.statusBarHeight
import dev.reprator.haat.ui.components.isAtTop

@Stable
class CollapsingToolbarState internal constructor(
    val listState: LazyListState,
    val maxCollapsePx: Float
) {
    var scrollOffset by mutableFloatStateOf(0f)
        internal set

    var collapseProgress by mutableFloatStateOf(0f)
        internal set

    var shouldShowSticky by mutableStateOf(false)
        internal set

    var listTopPadding by mutableStateOf(0.dp)
        internal set

    suspend fun scrollToTop() {
        animate(
            initialValue = scrollOffset,
            targetValue = 0f
        ) { value, _ ->
            scrollOffset = value
        }

        listState.animateScrollToItem(0)
    }
}


@Composable
fun rememberCollapsingToolbarState(
    listState: LazyListState,
    storeContainer: ModelUIStoreContainer?,
    statusHeight: Dp = statusBarHeight
): CollapsingToolbarState {

    val density = density

    val maxOffsetPx = remember {
        with(density) {
            (TOOLBAR_EXPANDED_HEIGHT - HEADER_TOOLBAR_SEARCH_ROW_HEIGHT + 16.dp).toPx()
        }
    }

    val state = remember(listState, maxOffsetPx) {
        CollapsingToolbarState(listState, maxOffsetPx)
    }

    val availableIndex = remember(storeContainer) {
        storeContainer?.storeCategoryList?.indexOfFirst {
            it.category == StoreCategory.Available
        } ?: -1
    }

    val collapseProgress by remember {
        derivedStateOf {
            (-state.scrollOffset / state.maxCollapsePx).coerceIn(0f, 1f)
        }
    }

    val shouldShowSticky by remember(listState, availableIndex) {
        derivedStateOf {
            if (availableIndex < 0) {
                return@derivedStateOf false
            }

            if (listState.isAtTop()) {
                return@derivedStateOf false
            }

            val visibleItem = listState.layoutInfo.visibleItemsInfo
                .firstOrNull { it.index == availableIndex }

            if (visibleItem == null) {
                return@derivedStateOf false
            }

            val percent = -visibleItem.offset.toFloat() / visibleItem.size.toFloat()
            percent >= 0.7f
        }
    }

    val listTopPadding by remember(collapseProgress, shouldShowSticky) {
        val fixedHeaderExpandedHeight = LIST_PADDING_TOP

        derivedStateOf {
            val fixedHeaderCollapsedHeight =
                statusHeight + HEADER_TOOLBAR_SEARCH_ROW_HEIGHT + if (shouldShowSticky) {
                    HEADER_TOOLBAR_SEARCH_ROW_HEIGHT + 32.dp
                } else {
                    16.dp
                }

            val listTopPaddingLerp = lerp(
                fixedHeaderCollapsedHeight,
                fixedHeaderExpandedHeight,
                1f - collapseProgress
            )
            listTopPaddingLerp
        }
    }

    state.collapseProgress = collapseProgress
    state.shouldShowSticky = shouldShowSticky
    state.listTopPadding = listTopPadding

    return state
}

@Composable
fun rememberCollapsingToolbarNestedScroll(
    state: CollapsingToolbarState
): NestedScrollConnection {

    val listState = state.listState

    return remember(listState) {
        object : NestedScrollConnection {

            override fun onPreScroll(
                available: Offset,
                source: NestedScrollSource
            ): Offset {
                val delta = available.y
                val newOffset = (state.scrollOffset + delta)
                    .coerceIn(-state.maxCollapsePx, 0f)

                val scrollUp = delta < 0
                val scrollDown = delta > 0

                when {
                    scrollUp -> state.scrollOffset = newOffset

                    scrollDown && listState.isAtTop() ->
                        state.scrollOffset = newOffset
                }

                return Offset.Zero
            }
        }
    }
}
