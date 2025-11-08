package dev.reprator.haat.features.restaurant2pane.businessDetail.presentation.ui

import androidx.annotation.DrawableRes
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.lerp
import dev.reprator.haat.R
import dev.reprator.haat.features.restaurant2pane.businessDetail.domain.modal.ModelUIHeaderInfo
import dev.reprator.haat.features.restaurant2pane.businessDetail.domain.modal.ModelUIProductCategoryItem
import dev.reprator.haat.features.restaurant2pane.businessDetail.domain.modal.testModelUIHeaderInfo
import dev.reprator.haat.features.restaurant2pane.businessDetail.domain.modal.testModelUIImage
import dev.reprator.haat.features.restaurant2pane.businessDetail.domain.modal.testModelUIStoreMenuCategory
import dev.reprator.haat.features.restaurant2pane.businessDetail.presentation.StoreInfoAction
import dev.reprator.haat.features.restaurant2pane.restaurants.domain.modal.ModelUIImage
import dev.reprator.haat.ui.ThemePreviews
import dev.reprator.haat.ui.components.DynamicAsyncImage
import dev.reprator.haat.ui.components.app.statusBarHeight
import dev.reprator.haat.ui.theme.HaatTheme
import kotlinx.coroutines.launch

val HEADER_TOOLBAR_SEARCH_ROW_HEIGHT = 48.dp
val TOOLBAR_EXPANDED_HEIGHT = 285.dp
val LIST_PADDING_TOP = 211.dp


@Composable
fun HeaderCollapsableBanner(
    bannerImage: ModelUIImage,
    modifier: Modifier = Modifier,
    collapseProgressProvider: () -> Float = { 0f },
) {
    val bannerHeight = lerp(
        start = TOOLBAR_EXPANDED_HEIGHT,
        stop = HEADER_TOOLBAR_SEARCH_ROW_HEIGHT,
        fraction = collapseProgressProvider()
    )

    Box(
        modifier
            .fillMaxWidth()
            .height(bannerHeight)
            .graphicsLayer {
                translationY = collapseProgressProvider()
            }
    ) {
        DynamicAsyncImage(
            imageUrl = bannerImage.imageUrl,
            blurHash = bannerImage.blurHash,
            contentDescription = "Banner image",
            modifier = Modifier.fillMaxSize()
        )
    }
}

@Composable
fun HeaderFixedContainer(
    headerInfo: ModelUIHeaderInfo,
    onAction: OnAction,
    modifier: Modifier = Modifier,
    collapseProgressProvider: () -> Float = { 0f },
    isStickyCategoryVisible: Boolean = false,
    activeCategoryIndex: Int = 0,
) {
    val isLogoVisibleWhenCollapsed = (collapseProgressProvider() > 0.7f)
    val isStickyVisible = isLogoVisibleWhenCollapsed && isStickyCategoryVisible

    Column(
        verticalArrangement = Arrangement.spacedBy(12.dp),
        modifier = modifier.background(if (isLogoVisibleWhenCollapsed) MaterialTheme.colorScheme.onPrimary else Color.Transparent)
    ) {

        HeaderToolbarSearchContainer(
            icon = headerInfo.icon,
            onAction = onAction,
            isLogoVisibleWhenCollapsed = isLogoVisibleWhenCollapsed,
            modifier = Modifier
                .padding(
                    top = statusBarHeight,
                    bottom = if (isStickyVisible) 0.dp else 8.dp,
                    start = 24.dp,
                    end = 24.dp
                )
                .fillMaxWidth()
        )

        AnimatedVisibility(
            visible = isStickyVisible,
            enter = fadeIn(animationSpec = tween(durationMillis = 100)),
            exit = fadeOut(animationSpec = tween(durationMillis = 100))
        ) {
            HeaderStickyCategoryFilter(
                categories = headerInfo.categoryList,
                activeCategoryIndex = activeCategoryIndex,
                onCategorySelected = { },
                Modifier.padding(start = 24.dp, end = 24.dp, bottom = 12.dp)
            )
        }
    }
}

@Composable
fun CircleIconButton(
    @DrawableRes icon: Int,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .background(
                color = Color.White.copy(alpha = 0.85f),
                shape = CircleShape
            )
            .border(
                width = 1.dp,
                color = Color(0xFFE6E6F2),
                shape = CircleShape
            )
            .clickable(onClick = onClick),
        contentAlignment = Alignment.Center
    ) {
        Icon(
            imageVector = ImageVector.vectorResource(id = icon),
            contentDescription = null,
            tint = Color(0xFF1A1B3A),
            modifier = Modifier.size(24.dp)
        )
    }
}

@Composable
private fun HeaderToolbarSearchContainer(
    icon: ModelUIImage,
    onAction: OnAction,
    modifier: Modifier = Modifier,
    isLogoVisibleWhenCollapsed: Boolean = false
) {
    Row(
        modifier = modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {

        CircleIconButton(
            icon = R.drawable.icon_back,
            onClick = {
                onAction(StoreInfoAction.NavigateToBack)
            }, Modifier.size(HEADER_TOOLBAR_SEARCH_ROW_HEIGHT)
        )

        Spacer(modifier = Modifier.width(8.dp))

        Box(
            modifier = Modifier
                .weight(1f)
                .fillMaxWidth()
                .height(HEADER_TOOLBAR_SEARCH_ROW_HEIGHT)
                .background(
                    brush = Brush.linearGradient(
                        listOf(
                            Color.White.copy(alpha = 0.80f),
                            Color.White.copy(alpha = 0.65f)
                        )
                    ),
                    shape = CircleShape
                )
                .border(
                    width = 1.dp,
                    color = Color(0xFFE6E6F2),
                    shape = CircleShape
                ),
            contentAlignment = Alignment.CenterStart
        ) {

            Row(
                modifier = Modifier
                    .padding(start = 20.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    imageVector = ImageVector.vectorResource(id = R.drawable.icon_search),
                    contentDescription = null,
                    tint = Color(0xFF6E6D89),
                    modifier = Modifier.size(20.dp)
                )

                Spacer(modifier = Modifier.width(10.dp))

                Text(
                    text = "Search products",
                    color = Color(0xFF6E6D89),
                    style = MaterialTheme.typography.bodyLarge
                )

                Spacer(modifier = Modifier.weight(1f))

                AnimatedVisibility(
                    visible = isLogoVisibleWhenCollapsed,
                    enter = fadeIn(animationSpec = tween(durationMillis = 100)),
                    exit = fadeOut(animationSpec = tween(durationMillis = 100))
                ) {
                    DynamicAsyncImage(
                        imageUrl = "https://im-staging.haat.delivery/${icon.imageUrl}",
                        contentDescription = "Market Logo",
                        modifier = Modifier
                            .size(HEADER_TOOLBAR_SEARCH_ROW_HEIGHT)
                            .background(
                                brush = Brush.linearGradient(
                                    listOf(
                                        Color.White.copy(alpha = 0.80f),
                                        Color.White.copy(alpha = 0.65f)
                                    )
                                ),
                                shape = CircleShape
                            )
                            .border(
                                width = 1.dp,
                                color = Color(0xFFE6E6F2),
                                shape = CircleShape
                            )
                            .clip(CircleShape)
                    )
                }
            }
        }

        Spacer(modifier = Modifier.width(8.dp))

        CircleIconButton(
            icon = if (isLogoVisibleWhenCollapsed) R.drawable.icon_more else R.drawable.icon_share,
            onClick = {}, Modifier.size(HEADER_TOOLBAR_SEARCH_ROW_HEIGHT)
        )
    }
}


@Composable
private fun HeaderStickyCategoryFilter(
    categories: List<ModelUIProductCategoryItem>,
    activeCategoryIndex: Int,
    onCategorySelected: (Int) -> Unit,
    modifier: Modifier = Modifier,
    lazyRowState: LazyListState = rememberLazyListState()
) {
    val scope = rememberCoroutineScope()

    LaunchedEffect(activeCategoryIndex) {
        if (-1 != activeCategoryIndex) {
            scope.launch {
                lazyRowState.animateScrollToItem(activeCategoryIndex)
            }
        }
    }

    LazyRow(
        modifier = modifier.fillMaxWidth(),
        state = lazyRowState,
        horizontalArrangement = Arrangement.spacedBy(8.dp),
    ) {
        itemsIndexed(categories, key = { _, item -> item.id }) { index, item ->
            val isActive = index == activeCategoryIndex
            HeaderCategoryFilterChipItem(
                categoryName = item.name,
                isActive = isActive,
                onClick = { onCategorySelected(index) }
            )
        }
    }
}

@Composable
private fun HeaderCategoryFilterChipItem(
    categoryName: String,
    isActive: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    val backgroundColor =
        if (isActive) MaterialTheme.colorScheme.primaryContainer else MaterialTheme.colorScheme.surfaceVariant

    Card(
        modifier = modifier
            .height(HEADER_TOOLBAR_SEARCH_ROW_HEIGHT)
            .clickable(onClick = onClick),
        shape = CircleShape,
        colors = CardDefaults.cardColors(containerColor = backgroundColor),
        elevation = CardDefaults.cardElevation(defaultElevation = 1.dp)
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 16.dp),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = categoryName,
                style = MaterialTheme.typography.labelLarge,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
        }
    }
}


@ThemePreviews
@Composable
private fun HeaderCollapsableBannerPreview() {
    HaatTheme {
        HeaderCollapsableBanner(
            bannerImage = testModelUIHeaderInfo.bannerImage,
            modifier = Modifier.fillMaxWidth(),
        )
    }
}

@ThemePreviews
@Composable
private fun HeaderCollapsableBannerCollapsedPreview() {
    HaatTheme {
        HeaderCollapsableBanner(
            bannerImage = testModelUIHeaderInfo.bannerImage,
            modifier = Modifier.fillMaxWidth(),
        )
    }
}

@ThemePreviews
@Composable
private fun HeaderFixedContainerPreview() {
    HaatTheme {
        HeaderFixedContainer(
            testModelUIHeaderInfo,
            {}, Modifier.fillMaxWidth()
        )
    }
}

@ThemePreviews
@Composable
private fun HeaderToolbarSearchContainerExpandedPreview() {
    HaatTheme {
        HeaderToolbarSearchContainer(
            testModelUIImage, {},
            Modifier.fillMaxWidth()
        )
    }
}

@ThemePreviews
@Composable
private fun HeaderToolbarSearchContainerCollapsedPreview() {
    HaatTheme {
        HeaderToolbarSearchContainer(
            testModelUIImage, {},
            Modifier.fillMaxWidth()
        )
    }
}

@ThemePreviews
@Composable
private fun StickyCategoryFilterSelectedPreview() {
    HaatTheme {
        HeaderStickyCategoryFilter(testModelUIStoreMenuCategory.productList as List<ModelUIProductCategoryItem>, 1, {})
    }
}

@ThemePreviews
@Composable
private fun StickyCategoryFilterPreview() {
    HaatTheme {
        HeaderStickyCategoryFilter(testModelUIStoreMenuCategory.productList as List<ModelUIProductCategoryItem>, -1, {})
    }
}

@ThemePreviews
@Composable
private fun HeaderCategoryFilterChipItemActivePreview() {
    HaatTheme {
        HeaderCategoryFilterChipItem("Fruite", true, {})
    }
}

@ThemePreviews
@Composable
private fun HeaderCategoryFilterChipItemPreview() {
    HaatTheme {
        HeaderCategoryFilterChipItem("Fruite", false, {})
    }
}