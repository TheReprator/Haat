package dev.reprator.haat.ui.components

import androidx.compose.foundation.lazy.grid.LazyGridState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.times

data class ListSize(val numberOfColumns: Int, val componentHeight: Dp, val totalCount: Int, val viewPortSize: Dp)

@Composable
fun rememberViewPortHeight(item: ListSize, state: LazyGridState): Dp {

    return remember(item, state) {
        val numberOfColumns = item.numberOfColumns
        val componentHeight = item.componentHeight
        val updateViewPortHeight = if (numberOfColumns >= item.totalCount) {
            componentHeight
        } else {
            val result = item.totalCount/ numberOfColumns
            val size = if (item.totalCount % numberOfColumns == 0)
                result
            else
                result + 1
            (size.times(componentHeight))
        }

        minOf(item.viewPortSize, updateViewPortHeight)
    }
}