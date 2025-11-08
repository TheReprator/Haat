package dev.reprator.haat.ui.components.scroll

import androidx.compose.foundation.gestures.ScrollableState
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.grid.LazyGridState
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.input.nestedscroll.NestedScrollConnection
import androidx.compose.ui.input.nestedscroll.NestedScrollSource
import kotlin.math.abs
import kotlin.math.min

//https://medium.com/@karol.ksionek/jetpack-compose-and-nested-scrolling-cbae2880312e
class NestedVerticalGridsScrollConnection(
    private val lazyColumnState: LazyListState,
    private val innerScrollableStates: List<ScrollableState>,
) : NestedScrollConnection {

    override fun onPreScroll(available: Offset, source: NestedScrollSource): Offset {
        if (available.y == 0f) {
            return Offset.Zero
        }
        val isScrollingDown = available.y < 0
        var offsetToProcess = abs(available.y)
        while (offsetToProcess != 0f) {
            if (lazyColumnState.hasReachedScrollBoundary(isScrollingDown)) {
                return Offset.Zero
            }
            when (val gridScrollData = buildScrollData(isScrollingDown)) {
                ScrollData.NoVerticalScrollable -> {
                    return Offset.Zero
                }

                is ScrollData.VerticalScrollData -> {
                    offsetToProcess -= processOffset(
                        offset = offsetToProcess,
                        isScrollingDown = isScrollingDown,
                        scrollData = gridScrollData,
                    )
                }
            }
        }
        return available
    }

    private fun buildScrollData(isScrollingDown: Boolean): ScrollData {
        val verticalScrollable = lazyColumnState.layoutInfo.visibleItemsInfo
            .filter {
                (0 <= it.index) && (innerScrollableStates.size > it.index)
            }.filter {
                innerScrollableStates[it.index].isVertical()
            }

        if (verticalScrollable.isEmpty())
            return ScrollData.NoVerticalScrollable

        val scrollableToCheck = if (isScrollingDown) {
            verticalScrollable.last()
        } else {
            verticalScrollable.first()
        }
        val lazyState = innerScrollableStates[scrollableToCheck.index]

        val canScroll = if (isScrollingDown) {
            lazyState.canScrollForward
        } else {
            lazyState.canScrollBackward
        }

        val scrollableStartOffset = scrollableToCheck.offset
        val scrollBoundaryOffset = if (isScrollingDown) {
            -lazyColumnState.layoutInfo.beforeContentPadding - lazyColumnState.layoutInfo.afterContentPadding
        } else {
            0
        }
        val desiredOffset =
            if (!canScroll && scrollableStartOffset == -lazyColumnState.layoutInfo.beforeContentPadding) {
                scrollBoundaryOffset
            } else {
                -lazyColumnState.layoutInfo.beforeContentPadding
            }

        return ScrollData.VerticalScrollData(
            visibleVerticalItemsCount = lazyColumnState.layoutInfo.visibleItemsInfo.size,
            scrollableState = lazyState,
            diffBetweenDesiredAndCurrentOffset = abs(desiredOffset - scrollableStartOffset).toFloat(),
            isAtDesiredOffsetAndCanBeScrolled = scrollableStartOffset == desiredOffset && canScroll,
        )
    }

    private fun processOffset(
        offset: Float,
        isScrollingDown: Boolean,
        scrollData: ScrollData.VerticalScrollData,
    ): Float {
        return when {
            scrollData.visibleVerticalItemsCount > 1 -> {
                scrollColumn(
                    offset = offset,
                    isScrollingDown = isScrollingDown,
                    withLimit = scrollData.diffBetweenDesiredAndCurrentOffset,
                )
            }

            scrollData.isAtDesiredOffsetAndCanBeScrolled -> {
                scrollInnerScrollable(
                    offset = offset,
                    scrollableState = scrollData.scrollableState,
                    isScrollingDown = isScrollingDown,
                )
            }

            else -> {
                val limit = if (scrollData.diffBetweenDesiredAndCurrentOffset == 0f) {
                    lazyColumnState.layoutInfo.mainAxisItemSpacing.coerceAtLeast(1).toFloat()
                } else {
                    scrollData.diffBetweenDesiredAndCurrentOffset
                }
                scrollColumn(
                    offset = offset,
                    isScrollingDown = isScrollingDown,
                    withLimit = limit,
                )
            }
        }
    }

    private fun scrollColumn(
        offset: Float,
        isScrollingDown: Boolean,
        withLimit: Float = Float.MAX_VALUE,
    ): Float {
        val toConsume = min(offset, withLimit)
        return if (isScrollingDown) {
            lazyColumnState.dispatchRawDelta(toConsume)
        } else {
            -lazyColumnState.dispatchRawDelta(-toConsume)
        }
    }

    private fun scrollInnerScrollable(
        offset: Float,
        scrollableState: ScrollableState,
        isScrollingDown: Boolean,
    ): Float {
        return if (isScrollingDown) {
            scrollableState.dispatchRawDelta(offset)
        } else {
            -scrollableState.dispatchRawDelta(-offset)
        }
    }

    private fun LazyListState.hasReachedScrollBoundary(isScrollingDown: Boolean): Boolean {
        return if (isScrollingDown) {
            val endOffset = layoutInfo.viewportSize.height - layoutInfo.beforeContentPadding
            !canScrollForward && layoutInfo.viewportEndOffset == endOffset
        } else {
            !canScrollBackward && layoutInfo.viewportStartOffset == -layoutInfo.beforeContentPadding
        }
    }

    private sealed interface ScrollData {

        data object NoVerticalScrollable : ScrollData

        data class VerticalScrollData(
            val visibleVerticalItemsCount: Int,
            val scrollableState: ScrollableState,
            val diffBetweenDesiredAndCurrentOffset: Float,
            val isAtDesiredOffsetAndCanBeScrolled: Boolean,
        ) : ScrollData
    }
}

fun ScrollableState.isVertical(): Boolean {
    return when (this) {
        is LazyListState, LazyGridState -> true
        else -> false
    }
}