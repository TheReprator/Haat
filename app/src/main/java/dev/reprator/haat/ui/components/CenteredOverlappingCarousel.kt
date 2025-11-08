package dev.reprator.haat.ui.components

import androidx.compose.foundation.interaction.collectIsDraggedAsState
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.collectLatest
import kotlin.math.abs

@Composable
fun CenteredOverlappingCarousel(
    count: Int,
    modifier: Modifier = Modifier
        .fillMaxWidth(),
    contentWidth: Dp = 200.dp,
    contentHeight: Dp = 200.dp,
    content: @Composable (modifier: Modifier, index: Int) -> Unit
) {
    val listState = rememberLazyListState(Int.MAX_VALUE / 2)
    val isDraggedState = listState.interactionSource.collectIsDraggedAsState()

    LaunchedEffect(isDraggedState) {
        snapshotFlow { isDraggedState.value }
            .collectLatest { isDragged ->
                if (!isDragged) {
                    while (true) {
                        delay(6 * 1_000L)
                        val nextIndex = listState.firstVisibleItemIndex + 1
                        listState.animateScrollToItem(nextIndex)
                    }
                }
            }
    }

    val density = LocalDensity.current

    BoxWithConstraints(modifier = modifier) {
        val viewportCenterPx = constraints.maxWidth / 2f
        val itemWidthPx = with(density) { contentWidth.toPx() }

        val horizontalPadding = with(density) {
            (viewportCenterPx - itemWidthPx / 2).toDp()
        }

        LazyRow(
            state = listState,
            modifier = Modifier
            .wrapContentHeight()
            .fillMaxWidth(),
            contentPadding = PaddingValues(horizontal = horizontalPadding),
            horizontalArrangement = Arrangement.spacedBy((-contentWidth / 3)),
            verticalAlignment = Alignment.CenterVertically
        ) {
            items(Int.MAX_VALUE) { globalIndex ->
                val scale by remember {
                    derivedStateOf {
                        val layoutInfo = listState.layoutInfo
                        val currentItem = layoutInfo.visibleItemsInfo
                            .firstOrNull { it.index == globalIndex }
                            ?: return@derivedStateOf 0.8f

                        // Get absolute center of this item relative to viewport
                        val itemCenter =
                            currentItem.offset + (currentItem.size / 2) - layoutInfo.viewportStartOffset

                        val distanceFromCenter = abs(itemCenter - viewportCenterPx)
                        val normalizedDistance =
                            (distanceFromCenter / viewportCenterPx).coerceIn(0f, 1f)

                        // Center item → 1f, far sides → 0.75f
                        1f - 0.25f * normalizedDistance
                    }
                }

                val scaledHeight = with(density) { contentHeight.toPx() * scale }

                Box(
                    modifier = Modifier
                        .width(contentWidth)
                        .height(with(density) { scaledHeight.toDp() })
                        .graphicsLayer {
                            scaleX = scale
                            scaleY = scale
                        }
                        .zIndex(scale)
                ) {
                    content(
                        Modifier.fillMaxSize(), globalIndex % count
                    )
                }
            }
        }
    }
}
