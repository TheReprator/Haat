package dev.reprator.haat.features.restaurant2pane.restaurants.presentation.ui

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.collectIsDraggedAsState
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawing
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyGridState
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.VerticalDivider
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import dev.reprator.haat.R
import dev.reprator.haat.features.restaurant2pane.businessDetail.presentation.ui.FloatingScrollToTopButton
import dev.reprator.haat.features.restaurant2pane.restaurants.domain.modal.CategoriesMarketHorizontal
import dev.reprator.haat.features.restaurant2pane.restaurants.domain.modal.HomeCategory
import dev.reprator.haat.features.restaurant2pane.restaurants.domain.modal.MainCategories
import dev.reprator.haat.features.restaurant2pane.restaurants.domain.modal.MarketHorizontaSubCategoryHighlight
import dev.reprator.haat.features.restaurant2pane.restaurants.domain.modal.MarketHorizontalSubCategoryNormal
import dev.reprator.haat.features.restaurant2pane.restaurants.domain.modal.MarketHorizontalSubCategorySponsered
import dev.reprator.haat.features.restaurant2pane.restaurants.domain.modal.ModelUIBannerContainer
import dev.reprator.haat.features.restaurant2pane.restaurants.domain.modal.ModelUIImage
import dev.reprator.haat.features.restaurant2pane.restaurants.domain.modal.ModelUIMarketContainer
import dev.reprator.haat.features.restaurant2pane.restaurants.domain.modal.ModelUIMarketHighlightContainer
import dev.reprator.haat.features.restaurant2pane.restaurants.domain.modal.ModelUIMarketStore
import dev.reprator.haat.features.restaurant2pane.restaurants.domain.modal.ModelUIMarketVerticalContainer
import dev.reprator.haat.features.restaurant2pane.restaurants.domain.modal.ModelUIMenuContainer
import dev.reprator.haat.features.restaurant2pane.restaurants.domain.modal.ModelUIPromotedBannerContainer
import dev.reprator.haat.features.restaurant2pane.restaurants.domain.modal.ModelUIRating
import dev.reprator.haat.features.restaurant2pane.restaurants.domain.modal.ModelUISuggestedMarket
import dev.reprator.haat.features.restaurant2pane.restaurants.domain.modal.ModelUISuggestedMarketContainer
import dev.reprator.haat.features.restaurant2pane.restaurants.domain.modal.ModelUITag
import dev.reprator.haat.features.restaurant2pane.restaurants.domain.modal.ModelUITagContainer
import dev.reprator.haat.features.restaurant2pane.restaurants.domain.modal.testBannerContainer
import dev.reprator.haat.features.restaurant2pane.restaurants.domain.modal.testCenteredOverlapCarouselData
import dev.reprator.haat.features.restaurant2pane.restaurants.domain.modal.testMarketStore
import dev.reprator.haat.features.restaurant2pane.restaurants.domain.modal.testModelUIMarketContainer
import dev.reprator.haat.features.restaurant2pane.restaurants.domain.modal.testModelUIMarketHighlightContainer
import dev.reprator.haat.features.restaurant2pane.restaurants.domain.modal.testModelUIMarketSponsoredContainer
import dev.reprator.haat.features.restaurant2pane.restaurants.domain.modal.testModelUIMarketVerticalContainer
import dev.reprator.haat.features.restaurant2pane.restaurants.domain.modal.testModelUIPromotedBannerContainer
import dev.reprator.haat.features.restaurant2pane.restaurants.domain.modal.testTagContainer
import dev.reprator.haat.features.restaurant2pane.restaurants.presentation.HomeMenuAction
import dev.reprator.haat.features.restaurant2pane.restaurants.presentation.HomeMenuState
import dev.reprator.haat.features.restaurant2pane.restaurants.presentation.HomeMenuViewModel
import dev.reprator.haat.ui.ThemePreviews
import dev.reprator.haat.ui.components.CenteredOverlappingCarousel
import dev.reprator.haat.ui.components.DynamicAsyncImage
import dev.reprator.haat.ui.components.ListSize
import dev.reprator.haat.ui.components.WidgetLoader
import dev.reprator.haat.ui.components.WidgetRetry
import dev.reprator.haat.ui.components.app.HaatSectionHeader
import dev.reprator.haat.ui.components.indicator.PagerWormIndicator
import dev.reprator.haat.ui.components.rememberViewPortHeight
import dev.reprator.haat.ui.components.scroll.rememberSingleNestedScroll
import dev.reprator.haat.ui.components.shouldShowScrollToTop
import dev.reprator.haat.ui.theme.HaatTheme
import dev.reprator.haat.util.composeColor
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

private typealias OnAction = (HomeMenuAction) -> Unit

@Composable
internal fun RestaurantScreen(
    onBusinessClick: (String) -> Unit,
    modifier: Modifier = Modifier,
    shouldHighlightSelectedBusiness: Boolean = false,
    viewModel: HomeMenuViewModel = hiltViewModel(),
) {
    val state: HomeMenuState by viewModel.uiState.collectAsStateWithLifecycle()

    LaunchedEffect(Unit) {
        if (state == HomeMenuState.initial()) {
            viewModel.onAction(HomeMenuAction.FetchHomeMenu)
        }
    }

    RestaurantScreenRootContainer(state, onAction = {
        when(it) {
            is HomeMenuAction.OpenStore -> {
                onBusinessClick(it.storeId)
            }

            else -> {
                viewModel.onAction(it)
            }
        }
    }, modifier.fillMaxSize()
        .windowInsetsPadding(WindowInsets.safeDrawing))
}

@Composable
private fun RestaurantScreenRootContainer(
    state: HomeMenuState,
    onAction: OnAction,
    modifier: Modifier = Modifier
) {
    when {
        state.isLoading -> {
            WidgetLoader(modifier)
        }

        state.isError -> {
            WidgetRetry(state.errorMessage, {
                onAction(HomeMenuAction.RetryHomeMenu)
            }, modifier = modifier)
        }

        else -> {
            RestaurantScreenBody(state.menuData, onAction, modifier)
        }
    }
}

@Composable
private fun RestaurantScreenBody(
    state: ModelUIMenuContainer,
    onAction: OnAction,
    modifier: Modifier = Modifier,
    shouldHighlightSelectedBusiness: Boolean = false,
) {
    Box(
        contentAlignment = Alignment.Center,
        modifier = modifier
    ) {
        when {
            state.userInfo.showDisruptionMessage -> {
                RestaurantScreenDisruption(state.userInfo.addressHintMessage)
            }

            else -> {
                RestaurantScreenBodyActual(
                    state,
                    onAction,
                    shouldHighlightSelectedBusiness = shouldHighlightSelectedBusiness
                )
            }
        }
    }
}


@Composable
private fun RestaurantScreenBodyActual(
    state: ModelUIMenuContainer,
    onAction: OnAction,
    modifier: Modifier = Modifier,
    shouldHighlightSelectedBusiness: Boolean = false,
) {
    val listState = rememberLazyListState()
    val coroutineScope = rememberCoroutineScope()

    val scrollToIndex: (Int) -> Unit = remember(listState) {
        { index: Int ->
            coroutineScope.launch {
                listState.animateScrollToItem(0)
            }
        }
    }

    Box(modifier = modifier.fillMaxSize()) {

        Column {
            HomeTopBar(
                state.userInfo.address, {}, {},
                Modifier.background(MaterialTheme.colorScheme.onPrimary)
            )

            RestaurantScreenBodyList(
                state.categories,
                onAction,
                shouldHighlightSelectedBusiness = shouldHighlightSelectedBusiness,
                lazyColumnState = listState
            )
        }

        AnimatedVisibility(visible = listState.shouldShowScrollToTop(), enter = fadeIn(),
            exit = fadeOut(), modifier = Modifier.align(Alignment.BottomStart).padding(start = 16.dp, bottom = 16.dp)) {
            FloatingScrollToTopButton(
                onClick = {
                    scrollToIndex(0)
                }
            )
        }
    }
}

@Composable
private fun RestaurantScreenBodyList(
    categories: List<MainCategories>,
    onAction: OnAction,
    modifier: Modifier = Modifier,
    shouldHighlightSelectedBusiness: Boolean = false,
    lazyColumnState: LazyListState = rememberLazyListState()
) {
    val rememberSingleNestedScroll = rememberSingleNestedScroll(lazyColumnState)

    LazyColumn(
        modifier = modifier.fillMaxSize()
            .nestedScroll(rememberSingleNestedScroll.nestedScrollConnection),
        verticalArrangement = Arrangement.spacedBy(12.dp),
        state = lazyColumnState,
    ) {
        itemsIndexed(
            categories, key = { _, item -> item.id },
            contentType = { _, item -> item.category }
        ) { index, item ->
            when (item.category) {
                HomeCategory.MainPageBanners -> {
                    CategoryBannerPagerContainer(item as ModelUIBannerContainer, onAction)
                }

                HomeCategory.Tags -> {
                    CategoryTagContainer(item as ModelUITagContainer, onAction)
                }

                HomeCategory.SuggestedMarketsCategory -> {
                    CategorySuggestedMarketContainer(item as ModelUISuggestedMarketContainer,
                        onAction,
                        state = rememberSingleNestedScroll.innerScrollableState.getOrCreateStateForGrid(index),
                        viewportHeight = rememberSingleNestedScroll.viewportHeight)
                }

                HomeCategory.GeneralPromotedBanner, HomeCategory.MarketPromotedBanner -> {
                    CategoryPromotedBannerContainer(item as ModelUIPromotedBannerContainer, onAction)
                }

                HomeCategory.MarketVerticalCategory -> {
                    CategoriesModelUIMarketVerticalContainer(item as ModelUIMarketVerticalContainer,
                        onAction,
                        state = rememberSingleNestedScroll.innerScrollableState.getOrCreateStateForGrid(index) ,
                        viewportHeight = rememberSingleNestedScroll.viewportHeight)
                }

                HomeCategory.MarketHorizontalCategory -> {
                    RestaurantScreenHorizontalCategory(
                        item as CategoriesMarketHorizontal, onAction,
                        shouldHighlightSelectedBusiness = shouldHighlightSelectedBusiness
                    )
                }
            }
        }
    }
}


@Composable
private fun RestaurantScreenHorizontalCategory(
    item: CategoriesMarketHorizontal,
    onAction: OnAction,
    modifier: Modifier = Modifier,
    shouldHighlightSelectedBusiness: Boolean = false
) {
    when (item.subType) {
        MarketHorizontalSubCategoryNormal -> {
            CategoriesModelUIMarketNormalContainer(item as ModelUIMarketContainer, onAction, modifier, shouldHighlightSelectedBusiness)
        }

        MarketHorizontalSubCategorySponsered -> {
            CategoriesModelUIMarketSponsoredContainer(item, onAction, modifier, shouldHighlightSelectedBusiness)
        }

        MarketHorizontaSubCategoryHighlight -> {
            CategoriesModelUIMarketHighlightContainer(item as ModelUIMarketHighlightContainer, onAction, modifier, shouldHighlightSelectedBusiness)
        }
    }
}

@Composable
private fun RestaurantScreenDisruption(
    message: String,
    modifier: Modifier = Modifier
) {
    Text(
        text = message,
        style = MaterialTheme.typography.titleMedium,
        modifier = modifier
    )
}

@Composable
private fun CategoriesModelUIMarketVerticalContainer(item: ModelUIMarketVerticalContainer,
                                                     onAction: OnAction,
                                                     viewportHeight: Dp,
                                                     modifier: Modifier = Modifier,
                                                     state: LazyGridState = rememberLazyGridState()) {

    Box(modifier = modifier.background(MaterialTheme.colorScheme.onPrimary)
        .padding(vertical = 16.dp)) {

        Column {
            HaatSectionHeader(item.name, showViewAll = item.isViewAll)
            Spacer(Modifier.fillMaxWidth().height(12.dp))
            CategoriesMarketVerticalStoreContainer(item.stores, onAction, viewportHeight = viewportHeight,
                state = state, modifier = Modifier.padding(horizontal = 16.dp))
        }

        if (item.isViewAll) {
            Button(
                onClick = {},
                modifier = Modifier
                    .padding(horizontal = 16.dp)
                    .fillMaxWidth()
                    .align(Alignment.BottomCenter)
                    .border(
                        width = 1.dp,
                        color = MaterialTheme.colorScheme.primary,
                        shape = MaterialTheme.shapes.small
                    ),
                shape = MaterialTheme.shapes.small,
                colors = ButtonDefaults.buttonColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer.copy(alpha = 0.5f),
                    contentColor = MaterialTheme.colorScheme.primary
                ),
                elevation = ButtonDefaults.buttonElevation(defaultElevation = 0.dp)
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Center
                ) {
                    Text(
                        text = "Show more",
                        style = MaterialTheme.typography.titleMedium
                    )
                    Icon(
                        painter = painterResource(id = R.drawable.icon_dropdown),
                        contentDescription = "Expand",
                    )
                }
            }
        }
    }
}

@Composable
private fun CategoriesMarketVerticalStoreContainer(
    stores: List<ModelUIMarketStore>,
    onAction: OnAction,
    viewportHeight: Dp,
    modifier: Modifier = Modifier,
    state: LazyGridState = rememberLazyGridState()) {

    val numberOfColumns = 2
    val updatedViewPortHeight = rememberViewPortHeight(ListSize(numberOfColumns,
        (221.dp + 16.dp), stores.size, viewportHeight), state)

    LazyVerticalGrid(
        modifier = modifier.heightIn(max = updatedViewPortHeight),
        columns = GridCells.Fixed(numberOfColumns),
        verticalArrangement = Arrangement.spacedBy(16.dp), // Spacing between rows
        horizontalArrangement = Arrangement.spacedBy(16.dp), // Spacing between columns
        state = state,
    ) {
        items(
            stores, key = { item -> item.id }
        ) { item ->
            CategoriesMarketHorizontalStoreItem(item, onAction, Modifier.width(148.dp).height(221.dp))
        }
    }
}

@Composable
private fun CategoriesModelUIMarketNormalContainer(
    item: ModelUIMarketContainer,
    onAction: OnAction,
    modifier: Modifier = Modifier,
    shouldHighlightSelectedBusiness: Boolean = false
) {
    Column(modifier = modifier
        .background(MaterialTheme.colorScheme.onPrimary)
        .padding(vertical = 16.dp)) {

        HaatSectionHeader(item.name)
        Spacer(Modifier.fillMaxWidth().height(8.dp))
        CategoriesMarketHorizontalStoreContainer(
            item.stores,
            onAction,
            shouldHighlightSelectedBusiness = shouldHighlightSelectedBusiness
        )
    }
}


@Composable
private fun CategoriesModelUIMarketSponsoredContainer(
    item: CategoriesMarketHorizontal,
    onAction: OnAction,
    modifier: Modifier = Modifier,
    shouldHighlightSelectedBusiness: Boolean = false
) {
    Column(modifier = modifier.background(MaterialTheme.colorScheme.onPrimary)
        .padding(vertical = 16.dp)) {

        HaatSectionHeader(item.name) {
            Text(
                text = "Sponsored",
                style = MaterialTheme.typography.bodySmall
            )
        }

        Spacer(Modifier.fillMaxWidth().height(12.dp))

        CategoriesMarketHorizontalStoreContainer(
            item.stores,
            onAction,
            shouldHighlightSelectedBusiness = shouldHighlightSelectedBusiness
        )
    }
}

@Composable
private fun CategoriesMarketHorizontalStoreContainer(
    stores: List<ModelUIMarketStore>,
    onAction: OnAction,
    modifier: Modifier = Modifier,
    shouldHighlightSelectedBusiness: Boolean = false,
    state: LazyListState = rememberLazyListState()
) {
    LazyRow(
        modifier = modifier,
        contentPadding = PaddingValues(start = 16.dp),
        horizontalArrangement = Arrangement.spacedBy(12.dp),
        state = state
    ) {
        items(
            stores, key = { item -> item.id }
        ) { item ->
            CategoriesMarketHorizontalStoreItem(item, onAction, Modifier.width(148.dp).height(221.dp))
        }
    }
}


@Composable
private fun CategoriesMarketHorizontalStoreItem(
    marketStore: ModelUIMarketStore,
    onAction: OnAction,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier.clickable{
            onAction(HomeMenuAction.OpenStore(marketStore.id))
        },
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Column(verticalArrangement = Arrangement.Center,
            modifier = Modifier.padding(4.dp)) {

            DynamicAsyncImage(
                imageUrl = "https://im-staging.haat.delivery/${marketStore.image.imageUrl}",
                contentDescription = "Business Name ${marketStore.name}",
                blurHash = marketStore.image.blurHash,
                modifier = Modifier
                    .width(85.dp)
                    .height(85.dp)
                    .clip(CircleShape)
                    .align(Alignment.CenterHorizontally)
            )

            Text(
                text = marketStore.name,
                style = MaterialTheme.typography.titleMedium,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )

            Text(
                text = marketStore.address,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                style = MaterialTheme.typography.labelMedium
            )

            HorizontalDivider(
                color = MaterialTheme.colorScheme.primary,
                thickness = 1.dp,
                modifier = Modifier.fillMaxWidth()
            )

            RatingContainer(marketStore.rating, marketStore.isNew)

            Text(
                text = marketStore.status.stringId,
                style = MaterialTheme.typography.titleSmall,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
        }
    }
}

@Composable
private fun RatingContainer(rating: ModelUIRating?, isNew: Boolean) {
    if (null != rating) {
        Row {
            Image(
                painter = painterResource(id = R.drawable.icon_rating),
                contentDescription = "Rating Logo",
            )

            Text(
                text = rating.value,
                style = MaterialTheme.typography.labelMedium,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )

            Text(
                text = "(${rating.numberOfRatings})",
                style = MaterialTheme.typography.bodySmall,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
        }
    } else if (isNew) {
        Text(
            text = "New!",
            style = MaterialTheme.typography.labelMedium,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis
        )
    }
}



@Composable
private fun CategoriesModelUIMarketHighlightContainer(
    item: ModelUIMarketHighlightContainer,
    onAction: OnAction,
    modifier: Modifier = Modifier,
    shouldHighlightSelectedBusiness: Boolean = false
) {
    Box(modifier.background(color = item.backgroundColor.composeColor)) {
        Column {
            DynamicAsyncImage(
                imageUrl = "https://im-staging.haat.delivery/${item.image.imageUrl}",
                contentDescription = "Background for ${item.name}",
                blurHash = item.image.blurHash,
                modifier = Modifier
                    .width(375.dp)
                    .height(144.dp)
            )

            CategoriesMarketHorizontalStoreContainer(
                item.stores,
                onAction,
                shouldHighlightSelectedBusiness = shouldHighlightSelectedBusiness,
                modifier = Modifier.padding(vertical = 16.dp)
            )
        }
    }
}


@Composable
private fun CategoryPromotedBannerContainer(promotedBannerContainer: ModelUIPromotedBannerContainer,
                                            onAction: OnAction,
                                            modifier: Modifier = Modifier) {
    CategorySuggestedBusinessItemImage(
        promotedBannerContainer.image,
        modifier = modifier
            .fillMaxWidth()
            .height(144.dp).clickable {
                onAction(HomeMenuAction.OpenStore(promotedBannerContainer.businessId))
            }
    )
}

@Composable
private fun CategorySuggestedMarketContainer(suggestMarketContainer: ModelUISuggestedMarketContainer,
                                             onAction: OnAction,
                                             viewportHeight: Dp,
                                             modifier: Modifier = Modifier,
                                             state: LazyGridState = rememberLazyGridState()) {

    Column(modifier = modifier.background(MaterialTheme.colorScheme.onPrimary)
        .padding(all = 16.dp)) {

        CategorySuggestedHeader(suggestMarketContainer.name, "Sponsored")
        Spacer(modifier = Modifier.width(12.dp))

        CategorySuggestedBusinessList(
            suggestMarketContainer.suggestedMarketList, onAction,
            viewportHeight = viewportHeight,
            state = state,
        )
    }
}

@Composable
private fun CategorySuggestedHeader(
    title: String,
    subtitle: String,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier
            .fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Start) {

        Image(
            painter = painterResource(id = R.drawable.icon_logo),
            contentDescription = "HAAT Logo",
            modifier = Modifier.height(40.dp)
        )

        Spacer(modifier = Modifier.width(12.dp))

        VerticalDivider(
            color = MaterialTheme.colorScheme.primary,
            modifier = Modifier
                .height(40.dp)
                .width(1.dp)
        )

        Spacer(modifier = Modifier.width(12.dp))

        Column(
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                text = title,
                color = MaterialTheme.colorScheme.primary,
                style = MaterialTheme.typography.titleMedium
            )
            Text(
                text = subtitle,
                color = MaterialTheme.colorScheme.primary,
                style = MaterialTheme.typography.bodyMedium
            )
        }
    }
}

@Composable
private fun CategorySuggestedBusinessList(
    imageBusinessList: List<ModelUISuggestedMarket>,
    onAction: OnAction,
    viewportHeight: Dp,
    modifier: Modifier = Modifier,
    state: LazyGridState = rememberLazyGridState()
) {
    val numberOfColumns = 1
    val updatedViewPortHeight = rememberViewPortHeight(ListSize(numberOfColumns,
        (250.dp + 16.dp), imageBusinessList.size, viewportHeight), state)

    LazyVerticalGrid(
        state = state,
        modifier = modifier.heightIn(max = updatedViewPortHeight),
        columns = GridCells.Fixed(numberOfColumns),
        contentPadding = PaddingValues(vertical = 12.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp),
    ) {
        items(imageBusinessList, key = { item -> item.id }
        ) { item ->
            CategorySuggestedBusinessItem(item, onAction,
                Modifier.fillMaxWidth().height(250.dp))
        }
    }
}

@Composable
private fun CategorySuggestedBusinessItem(
    suggestedMarket: ModelUISuggestedMarket,
    onAction: OnAction,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier.clickable{
            onAction(HomeMenuAction.OpenStore(suggestedMarket.id))
        },
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Box(contentAlignment = Alignment.BottomCenter) {

            DynamicAsyncImage(
                imageUrl = "https://im-staging.haat.delivery/${suggestedMarket.background.imageUrl}",
                contentDescription = "Background for ${suggestedMarket.title}",
                blurHash = suggestedMarket.background.blurHash,
                modifier = Modifier.fillMaxSize()
            )

            Column(
                modifier = Modifier
                    .padding(vertical = 8.dp)
                    .wrapContentHeight(),
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                Text(
                    text = suggestedMarket.title,
                    style = MaterialTheme.typography.headlineLarge,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                )

                Text(
                    text = suggestedMarket.subTitle,
                    style = MaterialTheme.typography.bodySmall,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                )

                CategorySuggestedBusinessItemImageCarousel(suggestedMarket.foreground)
            }
        }
    }
}

@Composable
private fun CategorySuggestedBusinessItemImageCarousel(
    foregroundList: List<ModelUIImage>,
    modifier: Modifier = Modifier
) {
    CenteredOverlappingCarousel(
        foregroundList.size,
        contentWidth = 109.dp,
        contentHeight = 90.dp,
        modifier = modifier
    ) { modifier, index ->
        CategorySuggestedBusinessItemImage(
            item = foregroundList[index], modifier
        )
    }
}


@Composable
private fun CategorySuggestedBusinessItemImage(
    item: ModelUIImage,
    modifier: Modifier = Modifier
) {
        DynamicAsyncImage(
            imageUrl = "https://im-staging.haat.delivery/${item.imageUrl}",
            contentDescription = "Carousel item",
            blurHash = item.blurHash,
            modifier = modifier
        )
}

@Composable
private fun HomeTopBar(
    userLocation: String,
    onMenuClick: () -> Unit,
    onSearchClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 8.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        IconButton(onClick = onMenuClick) {
            Icon(
                painter = painterResource(id = R.drawable.icon_hamburger),
                contentDescription = "Menu",
                modifier = Modifier.size(24.dp)
            )
        }

        Row(
            modifier = Modifier
                .weight(1f, fill = false)
                .padding(horizontal = 16.dp)
                .border(1.dp, Color.LightGray, CircleShape)
                .padding(horizontal = 16.dp, vertical = 8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {

            Image(
                painter = painterResource(id = R.drawable.icon_location),
                contentDescription = "Location"
            )

            Spacer(modifier = Modifier.width(8.dp))

            Text(
                text = userLocation,
                style = MaterialTheme.typography.titleMedium,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                modifier = Modifier.weight(1f, fill = false),
                color = MaterialTheme.colorScheme.onSurface
            )

            Spacer(modifier = Modifier.width(8.dp))

            Icon(
                painter = painterResource(id = R.drawable.icon_dropdown),
                contentDescription = "Dropdown",
                modifier = Modifier.padding(start = 4.dp)
            )
        }

        IconButton(onClick = onSearchClick) {
            Icon(
                painter = painterResource(id = R.drawable.icon_search),
                contentDescription = "Search",
                modifier = Modifier.size(24.dp)
            )
        }
    }
}

@Composable
private fun CategoryBannerPagerContainer(bannerContainer: ModelUIBannerContainer,
                                         onAction: OnAction,
                                         modifier: Modifier = Modifier) {
    val endlessPagerMultiplier = 1000
    val actualPageCount = bannerContainer.imageBannerList.size
    val pageCount = endlessPagerMultiplier * actualPageCount
    val pagerState = rememberPagerState(pageCount = { pageCount })

    val isDraggedState = pagerState.interactionSource.collectIsDraggedAsState()

    LaunchedEffect(isDraggedState) {
        snapshotFlow { isDraggedState.value }
            .collectLatest { isDragged ->
                if (!isDragged) {
                    while (true) {
                        delay(bannerContainer.interval * 1_000L)
                        pagerState.animateScrollToPage(pagerState.currentPage.inc() % pagerState.pageCount)
                    }
                }
            }
    }

    fun Int.absoluteIndex(): Int = this % actualPageCount

    Box(contentAlignment = Alignment.Center) {
        HorizontalPager(
            state = pagerState,
            modifier = modifier
                .fillMaxWidth()
                .height(200.dp),
            pageSpacing = 8.dp,
            key = { index ->
                bannerContainer.imageBannerList[index.absoluteIndex()].id
            }
        ) { page ->
            val banner = bannerContainer.imageBannerList[page.absoluteIndex()]

            Card(modifier = Modifier.fillMaxSize().clickable {
                onAction(HomeMenuAction.OpenStore(banner.id))
            }) {
                DynamicAsyncImage(
                    imageUrl = "https://im-staging.haat.delivery/${banner.image.imageUrl}",
                    contentDescription = null,
                    blurHash = banner.image.blurHash,
                    modifier = Modifier.fillMaxSize(),
                )
            }
        }

        PagerWormIndicator(
            pageCount = actualPageCount,
            currentPageFraction = remember {
                derivedStateOf { (pagerState.currentPage.absoluteIndex()) + pagerState.currentPageOffsetFraction }
            },
            activeDotColor = MaterialTheme.colorScheme.primary,
            activeDotSize = 12.dp,
            minDotSize = 4.dp,
            dotColor = MaterialTheme.colorScheme.outline,
        )
    }
}


@Composable
private fun CategoryTagContainer(modelUITagContainer: ModelUITagContainer,
                                 onAction: OnAction,
                                 modifier: Modifier = Modifier) {
    val state: LazyListState = rememberLazyListState()
    Column(modifier = modifier.background(MaterialTheme.colorScheme.onPrimary).padding(vertical = 16.dp)) {

        Text(text = modelUITagContainer.name, style = MaterialTheme.typography.titleMedium,
            maxLines = 1, overflow = TextOverflow.Ellipsis,
            modifier = Modifier.padding(horizontal = 16.dp))

        Spacer(Modifier.fillMaxWidth().height(4.dp))
        CategoryTags(modelUITagContainer.imageList, onAction, state = state)
    }
}

@Composable
private fun CategoryTags(
    tagsData: List<ModelUITag>,
    onAction: OnAction,
    modifier: Modifier = Modifier,
    state: LazyListState = rememberLazyListState()
) {
    LazyRow(
        modifier = modifier,
        contentPadding = PaddingValues(start = 16.dp),
        horizontalArrangement = Arrangement.spacedBy(16.dp),
        state = state
    ) {
        items(
            tagsData, key = { item -> item.id }
        ) { item ->
            CategoryTagItem(item, onAction)
        }
    }
}


@Composable
private fun CategoryTagItem(tag: ModelUITag, onAction: OnAction, modifier: Modifier = Modifier) {
    Card(
        modifier = modifier.clickable {
            onAction(HomeMenuAction.OpenStore(tag.id))
        },
        colors = CardDefaults.cardColors(MaterialTheme.colorScheme.surfaceContainerLow),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)) {

        Column(horizontalAlignment = Alignment.CenterHorizontally, modifier = Modifier.padding(4.dp)) {

            DynamicAsyncImage(
                imageUrl = "https://im-staging.haat.delivery/${tag.image.imageUrl}",
                contentDescription = null,
                blurHash = tag.image.blurHash,
                modifier = Modifier
                    .width(88.dp)
                    .height(88.dp).clip(CircleShape)
            )

            Spacer(Modifier.fillMaxWidth().height(4.dp))

            Text(
                text = tag.name,
                style = MaterialTheme.typography.titleSmall,
                maxLines = 1, overflow = TextOverflow.Ellipsis
            )
        }
    }
}


@ThemePreviews
@Composable
private fun CategoriesModelUIMarketVerticalContainerPreview() {
    HaatTheme {
        CategoriesModelUIMarketVerticalContainer(testModelUIMarketVerticalContainer, {}, 400.dp)
    }
}

@ThemePreviews
@Composable
private fun CategoriesModelUIMarketHighlightContainerPreview() {
    HaatTheme {
        CategoriesModelUIMarketHighlightContainer(testModelUIMarketHighlightContainer, {})
    }
}

@ThemePreviews
@Composable
private fun CategoriesModelUIMarketSponsoredContainerPreview() {
    HaatTheme {
        CategoriesModelUIMarketSponsoredContainer(testModelUIMarketSponsoredContainer, {})
    }
}

@ThemePreviews
@Composable
private fun CategoriesModelUIMarketNormalContainerPreview() {
    HaatTheme {
        CategoriesModelUIMarketNormalContainer(testModelUIMarketContainer, {})
    }
}

@ThemePreviews
@Composable
private fun CategoriesMarketHorizontalStoreItemPreview() {
    HaatTheme {
        CategoriesMarketHorizontalStoreItem(testMarketStore, {}, Modifier.width(148.dp).height(221.dp))
    }
}

@ThemePreviews
@Composable
private fun RatingContainerNotNewPreview() {
    HaatTheme {
        RatingContainer(ModelUIRating("4.3", "100+"), false)
    }
}

@ThemePreviews
@Composable
private fun RatingContainerNewPreview() {
    HaatTheme {
        RatingContainer(null, true)
    }
}

@ThemePreviews
@Composable
private fun CategoryPromotedBannerContainerPreview() {
    HaatTheme {
        CategoryPromotedBannerContainer(testModelUIPromotedBannerContainer, {})
    }
}

@ThemePreviews
@Composable
private fun CategorySuggestedBusinessItemImageCarouselPreview() {
    HaatTheme {
        CategorySuggestedBusinessItemImageCarousel(
            testCenteredOverlapCarouselData,
            Modifier.wrapContentHeight()
        )
    }
}

@ThemePreviews
@Composable
private fun SuggestedHeaderPreview() {
    HaatTheme {
        CategorySuggestedHeader(
            title = "Suggested Restaurants",
            subtitle = "Sponsored"
        )
    }
}

@ThemePreviews
@Composable
private fun HomeBannerPreview() {

    HaatTheme {
        CategoryBannerPagerContainer(testBannerContainer, {})
    }
}

@ThemePreviews
@Composable
private fun HomeTagsPreview() {
    HaatTheme {
        CategoryTagContainer(testTagContainer, {})
    }
}

@ThemePreviews
@Composable
private fun HomeTopBarPreviewEmpty() {
    HaatTheme {
        HomeTopBar("", {}, {})
    }
}

@ThemePreviews
@Composable
private fun HomeTopBarPreview() {
    HaatTheme {
        HomeTopBar("Dubai", {}, {})
    }
}


@ThemePreviews
@Composable
private fun HomeTopBarPreviewBig() {
    HaatTheme {
        HomeTopBar("DubaisfsdfsdfsdfsdfsdwerwrewrwerewrSinghVikram", {}, {})
    }
}

