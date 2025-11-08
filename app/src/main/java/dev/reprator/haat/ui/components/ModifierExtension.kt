package dev.reprator.haat.ui.components

import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.remember

val LazyListState.isAtEnd: Boolean
    get() {
        val lastVisibleItem = layoutInfo.visibleItemsInfo.lastOrNull() ?: return false
        return lastVisibleItem.index == layoutInfo.totalItemsCount - 1
    }


@Composable
fun LazyListState.shouldShowScrollToTop(): Boolean {
    return remember(this) {
        derivedStateOf {
            firstVisibleItemIndex > 0
        }
    }.value
}


fun LazyListState.isAtTop(): Boolean {
    return this.firstVisibleItemIndex == 0 &&
            this.firstVisibleItemScrollOffset == 0
}