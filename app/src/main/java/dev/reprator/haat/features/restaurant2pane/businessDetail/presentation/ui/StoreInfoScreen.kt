package dev.reprator.haat.features.restaurant2pane.businessDetail.presentation.ui


import androidx.annotation.DrawableRes
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
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
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import dev.reprator.haat.R
import dev.reprator.haat.features.restaurant2pane.businessDetail.domain.modal.ModelUIProductCategoryItem
import dev.reprator.haat.features.restaurant2pane.businessDetail.domain.modal.ModelUIStoreContainer
import dev.reprator.haat.features.restaurant2pane.businessDetail.domain.modal.ModelUIStoreInfo
import dev.reprator.haat.features.restaurant2pane.businessDetail.domain.modal.ModelUIStoreInfoHighlight
import dev.reprator.haat.features.restaurant2pane.businessDetail.domain.modal.ModelUIStoreMenuCategory
import dev.reprator.haat.features.restaurant2pane.businessDetail.domain.modal.ModelUIStoreMenuFooter
import dev.reprator.haat.features.restaurant2pane.businessDetail.domain.modal.ModelUIStoreMenuNotes
import dev.reprator.haat.features.restaurant2pane.businessDetail.domain.modal.ModelUIStoreMenuOther
import dev.reprator.haat.features.restaurant2pane.businessDetail.domain.modal.ModelUIStoreProductItem
import dev.reprator.haat.features.restaurant2pane.businessDetail.domain.modal.ModelUIStoreProductItem.Companion.CURRENCY_SYMBOL
import dev.reprator.haat.features.restaurant2pane.businessDetail.domain.modal.ModelUIStoreProductItemSubType
import dev.reprator.haat.features.restaurant2pane.businessDetail.domain.modal.StoreCategory
import dev.reprator.haat.features.restaurant2pane.businessDetail.domain.modal.StoreCategoryOtherOrientation
import dev.reprator.haat.features.restaurant2pane.businessDetail.domain.modal.StoreListCategory
import dev.reprator.haat.features.restaurant2pane.businessDetail.domain.modal.StoreMenuItemQuantityWeighable
import dev.reprator.haat.features.restaurant2pane.businessDetail.domain.modal.testModelUIStoreInfo
import dev.reprator.haat.features.restaurant2pane.businessDetail.domain.modal.testModelUIStoreMenuCategory
import dev.reprator.haat.features.restaurant2pane.businessDetail.domain.modal.testModelUIStoreMenuCategoryItem
import dev.reprator.haat.features.restaurant2pane.businessDetail.domain.modal.testModelUIStoreMenuFooter
import dev.reprator.haat.features.restaurant2pane.businessDetail.domain.modal.testModelUIStoreMenuNotes
import dev.reprator.haat.features.restaurant2pane.businessDetail.domain.modal.testModelUIStoreProductContainer
import dev.reprator.haat.features.restaurant2pane.businessDetail.domain.modal.testModelUIStoreMenuOtherVertical
import dev.reprator.haat.features.restaurant2pane.businessDetail.domain.modal.testModelUIStoreProductCountableDiscountItem
import dev.reprator.haat.features.restaurant2pane.businessDetail.domain.modal.testModelUIStoreProductCountableDiscountItemAdded
import dev.reprator.haat.features.restaurant2pane.businessDetail.domain.modal.testModelUIStoreProductCountableNoDiscountItem
import dev.reprator.haat.features.restaurant2pane.businessDetail.domain.modal.testModelUIStoreProductCountableNoDiscountItemAdded
import dev.reprator.haat.features.restaurant2pane.businessDetail.domain.modal.testModelUIStoreProductWeighAbleDiscountItem
import dev.reprator.haat.features.restaurant2pane.businessDetail.domain.modal.testModelUIStoreProductWeighAbleDiscountItemAdded
import dev.reprator.haat.features.restaurant2pane.businessDetail.domain.modal.testModelUIStoreProductWeighAbleDiscountItemAddedMultipleTime
import dev.reprator.haat.features.restaurant2pane.businessDetail.domain.modal.testModelUIStoreProductWeighAbleNoDiscountItem
import dev.reprator.haat.features.restaurant2pane.businessDetail.domain.modal.testModelUIStoreProductWeighAbleNoDiscountItemAdded
import dev.reprator.haat.features.restaurant2pane.businessDetail.domain.modal.testStoreListCategory
import dev.reprator.haat.features.restaurant2pane.businessDetail.domain.modal.testStoreModelUIStoreContainer
import dev.reprator.haat.features.restaurant2pane.businessDetail.presentation.ProductAction
import dev.reprator.haat.features.restaurant2pane.businessDetail.presentation.StoreInfoAction
import dev.reprator.haat.features.restaurant2pane.businessDetail.presentation.StoreInfoState
import dev.reprator.haat.features.restaurant2pane.businessDetail.presentation.StoreInfoViewModel
import dev.reprator.haat.features.restaurant2pane.businessDetail.presentation.ui.state.rememberCollapsingToolbarNestedScroll
import dev.reprator.haat.features.restaurant2pane.businessDetail.presentation.ui.state.rememberCollapsingToolbarState
import dev.reprator.haat.ui.ThemePreviews
import dev.reprator.haat.ui.components.DynamicAsyncImage
import dev.reprator.haat.ui.components.ListSize
import dev.reprator.haat.ui.components.WidgetLoader
import dev.reprator.haat.ui.components.WidgetRetry
import dev.reprator.haat.ui.components.app.HaatSectionHeader
import dev.reprator.haat.ui.components.isAtEnd
import dev.reprator.haat.ui.components.rememberViewPortHeight
import dev.reprator.haat.ui.components.scroll.rememberSingleNestedScroll
import dev.reprator.haat.ui.components.shouldShowScrollToTop
import dev.reprator.haat.ui.theme.HaatTheme
import kotlinx.coroutines.launch
import java.util.Locale

typealias OnAction = (StoreInfoAction) -> Unit

@Composable
fun StoreInfoScreen(
    showBackButton: Boolean,
    onBackClick: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: StoreInfoViewModel = hiltViewModel(),
) {
    val state by viewModel.uiState.collectAsStateWithLifecycle()

    LaunchedEffect(Unit) {
        if (state == StoreInfoState.initial()) {
            viewModel.onAction(StoreInfoAction.FetchStoreData)
        }
    }

    StoreInfoRootContainer(state, viewModel.storeId, onAction = {
        when(it) {
            is StoreInfoAction.NavigateToBack -> {
                onBackClick()
            } else -> {
                viewModel.onAction(it)
            }
        }
        viewModel.onAction(it)
    }, modifier.fillMaxSize())
}


@Composable
private fun StoreInfoRootContainer(
    state: StoreInfoState,
    storeId: String,
    onAction: OnAction,
    modifier: Modifier = Modifier,
) {
    when {
        state.isLoading -> {
            WidgetLoader(modifier)
        }

        state.isError -> {
            WidgetRetry(state.errorMessage, {
                onAction(StoreInfoAction.RetryStoreData)
            }, modifier = modifier)
        }

        state.storeData.storeCategoryList.isEmpty() -> {

        }

        else -> {
            StoreScreenContainerBody(state.storeData, storeId, onAction, modifier)
        }
    }
}

@Composable
private fun StoreScreenContainerBody(
    storeContainer: ModelUIStoreContainer,
    storeId: String,
    onAction: OnAction,
    modifier: Modifier = Modifier
) {
    val listState = rememberLazyListState()

    val toolbarState = rememberCollapsingToolbarState(listState, storeContainer)
    val nestedScrollConnection = rememberCollapsingToolbarNestedScroll(toolbarState)
    val coroutineScope = rememberCoroutineScope()


    val scrollToIndex: (Int) -> Unit = remember(listState) {
        { index: Int ->
            coroutineScope.launch {
                toolbarState.scrollToTop()
            }
        }
    }

    Box(
        modifier = modifier
            .fillMaxSize()
            .nestedScroll(nestedScrollConnection)
    ) {

        HeaderCollapsableBanner(
            bannerImage = storeContainer.headerInfo.bannerImage,
            collapseProgressProvider = { toolbarState.collapseProgress }
        )

        HeaderFixedContainer(
            headerInfo = storeContainer.headerInfo,
            onAction = onAction,
            isStickyCategoryVisible = toolbarState.shouldShowSticky,
            collapseProgressProvider = { toolbarState.collapseProgress },
            modifier = Modifier
                .align(Alignment.TopCenter)
        )

        StoreMenuScreenList(
            categories = storeContainer.storeCategoryList,
            onAction = onAction,
            lazyColumnState = listState,
            modifier = Modifier
                .background(Color.Transparent)
                .padding(top = toolbarState.listTopPadding)
        )

        StoreFooterContainer(
            listState.shouldShowScrollToTop(),
            scrollToTopCallback = scrollToIndex,
            storeContainer.orderInfo.storeOrders[storeId],
            Modifier
                .fillMaxWidth()
                .align(Alignment.BottomCenter)
        )
    }
}

@Composable
private fun StoreMenuScreenList(
    categories: List<StoreListCategory>,
    onAction: OnAction,
    modifier: Modifier = Modifier,
    lazyColumnState: LazyListState = rememberLazyListState()
) {
    val rememberSingleNestedScroll = rememberSingleNestedScroll(lazyColumnState)

    LazyColumn(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(16.dp),
        state = lazyColumnState
    ) {

        itemsIndexed(
            categories, key = { _, item -> item.id + item.category.name },
            contentType = { _, item -> item.category }
        ) { index, item ->
            when (item.category) {

                StoreCategory.HEADER_INFO -> {
                    StoreSectionHeaderContainer(storeInfo = item as ModelUIStoreInfo)
                }

                StoreCategory.Footer -> {
                    StoreSectionFooterComposable(item as ModelUIStoreMenuFooter)
                }

                StoreCategory.Notes -> {
                    StoreSectionNotesComposable(item as ModelUIStoreMenuNotes)
                }

                StoreCategory.Other -> {
                    StoreSectionProductsContainer(
                        item as ModelUIStoreMenuOther,
                        onAction,
                        state = rememberSingleNestedScroll.innerScrollableState.getOrCreateStateForGrid(
                            index
                        ),
                        viewportHeight = rememberSingleNestedScroll.viewportHeight
                    )
                }

                StoreCategory.Available -> {
                    StoreSectionCategoryContainer(
                        item as ModelUIStoreMenuCategory,
                        state = rememberSingleNestedScroll.innerScrollableState.getOrCreateStateForGrid(
                            index
                        ),
                        viewportHeight = rememberSingleNestedScroll.viewportHeight
                    )
                }
            }
        }
    }
}

@Composable
fun StoreSectionHeaderContainer(
    storeInfo: ModelUIStoreInfo,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier.padding(32.dp),
        shape = MaterialTheme.shapes.medium,
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.onPrimary),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
    ) {
        Column(
            modifier = Modifier.padding(24.dp),
            horizontalAlignment = Alignment.Start,
            verticalArrangement = Arrangement.Center
        ) {
            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.fillMaxWidth()
            ) {
                DynamicAsyncImage(
                    imageUrl = "https://im-staging.haat.delivery/${storeInfo.icon.imageUrl}",
                    contentDescription = "Icon Banner",
                    modifier = Modifier
                        .size(48.dp)
                        .clip(CircleShape)
                )

                Icon(
                    imageVector = ImageVector.vectorResource(id = R.drawable.icon_bookmark),
                    contentDescription = "Bookmark",
                    tint = MaterialTheme.colorScheme.onPrimaryContainer
                )
            }

            Spacer(modifier = Modifier.height(8.dp))

            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    text = storeInfo.name,
                    style = MaterialTheme.typography.headlineSmall,
                )

                Icon(
                    imageVector = ImageVector.vectorResource(id = R.drawable.icon_forward),
                    contentDescription = "Forward",
                    tint = MaterialTheme.colorScheme.onPrimaryContainer
                )
            }

            Text(
                text = storeInfo.address,
                style = MaterialTheme.typography.bodySmall
            )

            Text(
                text = storeInfo.time.time,
                style = MaterialTheme.typography.headlineSmall
            )
            Spacer(modifier = Modifier.height(8.dp))

            HorizontalDivider(thickness = 1.dp, color = MaterialTheme.colorScheme.primary)

            StoreSubSectionHeaderHighLightList(storeInfo.highLightList)

            StoreSubSectionHeaderOffer(
                R.drawable.icon_delivery,
                "-20% Sale",
                "on part of the items"
            )

            Spacer(modifier = Modifier.height(12.dp))

            StoreSubSectionHeaderOffer(
                R.drawable.icon_delivery,
                "0 Delivery ",
                "fee on part of the items"
            )
        }
    }
}

@Composable
private fun StoreSubSectionHeaderHighLightList(
    highlightList: List<ModelUIStoreInfoHighlight>, modifier: Modifier = Modifier,
    state: LazyListState = rememberLazyListState()
) {
    LazyRow(
        modifier = modifier, state = state,
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {

        items(highlightList, key = { item -> item.id }) { item ->

            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center,
                modifier = Modifier
                    .width(96.dp)
                    .height(109.dp)
            ) {
                CircleIconButton(
                    item.icon, {}, Modifier
                        .size(36.dp)
                )

                Text(
                    text = item.text,
                    style = MaterialTheme.typography.bodySmall,
                )

                if (item.isHighlightLabel) {
                    Card(
                        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.error),
                        shape = MaterialTheme.shapes.extraSmall,
                        modifier = Modifier.padding(bottom = 4.dp)
                    ) {
                        Text(
                            text = item.description,
                            style = MaterialTheme.typography.labelSmall,
                            modifier = Modifier.padding(horizontal = 4.dp, vertical = 2.dp)
                        )
                    }
                } else {
                    Text(
                        text = item.description,
                        style = MaterialTheme.typography.bodyMedium,
                    )
                }
            }
        }
    }
}

@Composable
private fun StoreSubSectionHeaderOffer(
    @DrawableRes icon: Int,
    saleTextBold: String,
    infoText: String, modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier,
        shape = MaterialTheme.shapes.medium,
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.onPrimary),
        border = BorderStroke(
            1.dp,
            MaterialTheme.colorScheme.surfaceDim
        )
    ) {

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {

            Icon(
                imageVector = ImageVector.vectorResource(id = icon),
                contentDescription = null,
                tint = MaterialTheme.colorScheme.error,
                modifier = Modifier.size(24.dp)
            )

            Spacer(modifier = Modifier.width(8.dp))

            Text(
                buildAnnotatedString {
                    withStyle(style = SpanStyle(fontWeight = FontWeight.Bold)) {
                        append(saleTextBold)
                    }
                    append(" ")
                    append(infoText)
                },
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                style = MaterialTheme.typography.bodyLarge,
                color = Color(0xFF1A1B3A)
            )
        }
    }
}

@Composable
private fun StoreSectionProductsContainer(
    productMenu: ModelUIStoreMenuOther,
    onAction: OnAction,
    viewportHeight: Dp,
    modifier: Modifier = Modifier,
    state: LazyGridState = rememberLazyGridState()
) {
    val lazyRowState = rememberLazyListState()
    val isEndReached by remember(lazyRowState) {
        derivedStateOf {
            lazyRowState.isAtEnd
        }
    }

    Column(modifier.background(MaterialTheme.colorScheme.onPrimary).padding(vertical = 16.dp)) {
        HaatSectionHeader(
            productMenu.name,
            showViewAll = !isEndReached && productMenu.orientationType == StoreCategoryOtherOrientation.Horizontal,
            showViewAllAction = {})

        Spacer(Modifier.fillMaxWidth().height(12.dp))

        if (productMenu.orientationType == StoreCategoryOtherOrientation.Horizontal) {
            StoreSectionProductsHorizontally(productMenu, onAction, state = lazyRowState)
        } else {
            StoreMenuProductsVertically(productMenu, onAction, viewportHeight = viewportHeight, state = state)
        }
    }
}

@Composable
private fun StoreSectionProductsHorizontally(
    productMenu: ModelUIStoreMenuOther,
    onAction: OnAction,
    modifier: Modifier = Modifier,
    state: LazyListState = rememberLazyListState(),
) {
    LazyRow(modifier, state = state,
        contentPadding = PaddingValues(16.dp),
        horizontalArrangement = Arrangement.spacedBy(16.dp)) {
        items(
            productMenu.productList,
            key = { item -> item.id },
            contentType = { it.productSubType }) { item ->

            when (item.productSubType) {
                ModelUIStoreProductItemSubType.Actual -> {
                    StoreSubSectionProductItem(
                        item as ModelUIStoreProductItem, onAction,
                        Modifier
                            .height(215.dp)
                            .width(120.dp)
                    )
                }

                ModelUIStoreProductItemSubType.Footer -> {
                    StoreSubSectionProductItemFooter()
                }
            }
        }
    }
}


@Composable
private fun StoreSubSectionProductItemFooter(modifier: Modifier = Modifier) {
    Column(
        modifier = modifier
            .height(220.dp)
            .width(120.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Box(
            modifier = Modifier
                .size(36.dp)
                .background(MaterialTheme.colorScheme.surface, CircleShape)
                .border(1.dp, MaterialTheme.colorScheme.error, CircleShape),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                imageVector = ImageVector.vectorResource(id = R.drawable.icon_forward_straight),
                contentDescription = "List End",
                tint = MaterialTheme.colorScheme.onPrimaryContainer
            )
        }

        Text(
            text = "View All",
            style = MaterialTheme.typography.titleMedium,
            color = MaterialTheme.colorScheme.error
        )

    }
}

@Composable
private fun StoreMenuProductsVertically(
    productMenu: ModelUIStoreMenuOther,
    onAction: OnAction,
    viewportHeight: Dp,
    modifier: Modifier = Modifier,
    state: LazyGridState = rememberLazyGridState()
) {
    val numberOfColumns = 3
    val updatedViewPortHeight = rememberViewPortHeight(
        ListSize(
            numberOfColumns,
            (215.dp + 16.dp), productMenu.productList.size, viewportHeight
        ), state
    )

    LazyVerticalGrid(
        state = state,
        columns = GridCells.Fixed(numberOfColumns),
        contentPadding = PaddingValues(horizontal = 16.dp, vertical = 8.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        modifier = modifier.heightIn(max = updatedViewPortHeight),
    ) {
        items(productMenu.productList, key = { it.id }) { item ->
            StoreSubSectionProductItem(
                item as ModelUIStoreProductItem, onAction, Modifier
                    .height(215.dp)
                    .width(110.dp)
            )
        }
    }
}

@Composable
private fun StoreSubSectionProductItemInfo(
    item: ModelUIStoreProductItem,
    modifier: Modifier = Modifier
) {
    Column(modifier) {

        Row {
            if (0 < item.pricing.discountPercentage) {
                Text(
                    text = String.format(
                        Locale.US, "$CURRENCY_SYMBOL%.2f",
                        item.pricing.showActualPrice
                    ),
                    style = MaterialTheme.typography.titleMedium,
                    color = MaterialTheme.colorScheme.error
                )

                Spacer(Modifier.width(8.dp))
            }

            Text(
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                text = String.format(Locale.US, "$CURRENCY_SYMBOL%.2f", item.pricing.basePrice),
                textDecoration = if (0 < item.pricing.discountPercentage) TextDecoration.LineThrough else TextDecoration.None,
                style = MaterialTheme.typography.titleMedium,
            )
        }

        Text(
            text = item.name,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
            style = MaterialTheme.typography.bodyLarge,
        )

        if (item.quantityWeight.quantityType is StoreMenuItemQuantityWeighable) {
            Text(
                text = item.quantityWeight.weightToPresent,
                style = MaterialTheme.typography.bodySmall,
            )
        }
    }
}


@Composable
private fun StoreSubSectionProductItem(
    item: ModelUIStoreProductItem,
    onAction: OnAction,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier,
        elevation = CardDefaults.cardElevation(defaultElevation = 1.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
    ) {
        Column(verticalArrangement = Arrangement.SpaceBetween) {
            Box(
                Modifier
                    .height(90.dp)
                    .width(115.dp)
            ) {

                DynamicAsyncImage(
                    imageUrl = "https://im-staging.haat.delivery/${item.image.imageUrl}",
                    blurHash = item.image.blurHash,
                    contentDescription = item.name
                )

                if (item.pricing.specialLabel.isNotEmpty()) {
                    Card(
                        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.error),
                        shape = MaterialTheme.shapes.extraSmall,
                        modifier = Modifier
                            .align(Alignment.BottomStart)
                            .padding(bottom = 4.dp)
                    ) {
                        Text(
                            text = item.pricing.specialLabel,
                            style = MaterialTheme.typography.labelSmall,
                            modifier = Modifier.padding(horizontal = 4.dp, vertical = 2.dp)
                        )
                    }
                }
            }

            Spacer(Modifier.weight(1f))

            StoreSubSectionProductItemInfo(item, Modifier.padding(12.dp))

            Spacer(Modifier.weight(1f))

            QuantitySelector(
                { onAction(StoreInfoAction.AddOperationToProduct(action= it, productItem = item ))  },
                text = if (item.isAdded) item.totalQuantity else "",
                modifier = Modifier
                    .padding(start = 4.dp, end = 4.dp, bottom = 2.dp)
                    .fillMaxWidth()
                    .height(32.dp)
            )
        }
    }
}

@Composable
private fun QuantitySelector(
    quantityAction : (ProductAction) -> Unit,
    modifier: Modifier = Modifier,
    text: String = "",
) {
    Card(
        modifier = modifier,
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.onPrimary),
        elevation = CardDefaults.cardElevation(defaultElevation = 1.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = if (text.isEmpty()) Arrangement.Center else Arrangement.SpaceBetween
        ) {
            if (!text.isEmpty()) {
                IconButton(
                    modifier = Modifier.size(24.dp),
                    onClick = { quantityAction(ProductAction.Substraction) },
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.icon_minus),
                        contentDescription = "Decrease",
                        tint = MaterialTheme.colorScheme.error,
                    )
                }

                Text(
                    text = text,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                    style = MaterialTheme.typography.bodySmall,
                    modifier = Modifier.align(Alignment.CenterVertically)
                )
            }

            IconButton(
                modifier = Modifier.size(24.dp),
                onClick = { quantityAction(ProductAction.Addition)},
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.icon_add),
                    contentDescription = "Increase",
                    tint = MaterialTheme.colorScheme.error,
                )
            }
        }
    }
}


@Composable
private fun StoreSectionCategoryContainer(
    productMenu: ModelUIStoreMenuCategory,
    viewportHeight: Dp,
    modifier: Modifier = Modifier,
    state: LazyGridState = rememberLazyGridState()
) {
    val lazyRowState = rememberLazyListState()
    val isEndReached by remember(lazyRowState) {
        derivedStateOf {
            lazyRowState.isAtEnd
        }
    }

    Column(modifier.background(MaterialTheme.colorScheme.onPrimary).padding(vertical = 16.dp)) {
        HaatSectionHeader(
            productMenu.name,
            showViewAll = !isEndReached && productMenu.orientationType == StoreCategoryOtherOrientation.Horizontal
        )
        Spacer(Modifier.fillMaxWidth().height(12.dp))

        if (productMenu.orientationType == StoreCategoryOtherOrientation.Horizontal) {
            StoreSectionCategoryHorizontally(productMenu, state = lazyRowState)
        } else {
            StoreSectionCategoryVertically(
                productMenu,
                viewportHeight = viewportHeight,
                state = state
            )
        }
    }
}

@Composable
private fun StoreSectionCategoryHorizontally(
    productMenu: ModelUIStoreMenuCategory,
    modifier: Modifier = Modifier,
    state: LazyListState = rememberLazyListState(),
) {
    LazyRow(modifier, state = state,
        contentPadding = PaddingValues(16.dp),
        horizontalArrangement = Arrangement.spacedBy(16.dp)) {
        items(
            productMenu.productList,
            key = { item -> item.id }, contentType = { it.productSubType }) { item ->

            when (item.productSubType) {
                ModelUIStoreProductItemSubType.Actual -> {
                    StoreSubSectionMenuCategoryItem(
                        item as ModelUIProductCategoryItem, Modifier
                            .height(215.dp)
                            .width(110.dp)
                    )
                }

                ModelUIStoreProductItemSubType.Footer -> {
                    StoreSubSectionProductItemFooter()
                }
            }
        }
    }
}

@Composable
private fun StoreSectionCategoryVertically(
    productGridContainer: ModelUIStoreMenuCategory,
    viewportHeight: Dp,
    modifier: Modifier = Modifier,
    state: LazyGridState = rememberLazyGridState()
) {
    val numberOfColumns = 2
    val updatedViewPortHeight = rememberViewPortHeight(
        ListSize(
            numberOfColumns,
            (130.dp + 12.dp), productGridContainer.productList.size, viewportHeight
        ), state
    )
    LazyVerticalGrid(
        state = state,
        contentPadding = PaddingValues(12.dp),
        columns = GridCells.Fixed(numberOfColumns),
        verticalArrangement = Arrangement.spacedBy(12.dp),
        horizontalArrangement = Arrangement.spacedBy(12.dp),
        modifier = modifier.heightIn(max = updatedViewPortHeight),
    ) {
        items(productGridContainer.productList, key = { it.id }) { product ->
            StoreSubSectionMenuCategoryItem(
                product as ModelUIProductCategoryItem, Modifier
                    .fillMaxWidth()
                    .height(130.dp)
            )
        }
    }
}

@Composable
private fun StoreSubSectionMenuCategoryItem(
    product: ModelUIProductCategoryItem,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier,
        elevation = CardDefaults.cardElevation(defaultElevation = 0.dp)
    ) {
        Box(Modifier.fillMaxSize()) {
            DynamicAsyncImage(
                imageUrl = "https://im-staging.haat.delivery/${product.image.imageUrl}",
                contentDescription = product.name,
                modifier = Modifier
                    .fillMaxSize()
            )

            Text(
                modifier = Modifier
                    .fillMaxWidth()
                    .align(Alignment.BottomStart)
                    .background(color = Color.White.copy(alpha = 0.8f))
                    .padding(16.dp),
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                text = product.name,
                style = MaterialTheme.typography.labelLarge,
            )
        }
    }
}

@Composable
private fun StoreSectionNotesComposable(
    notes: ModelUIStoreMenuNotes,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .background(MaterialTheme.colorScheme.onPrimary)
            .padding(16.dp)
    ) {
        Text(
            text = notes.header,
            style = MaterialTheme.typography.titleMedium,
        )

        Text(
            text = notes.description,
            style = MaterialTheme.typography.bodyMedium,
        )
    }
}

@Composable
private fun StoreSectionFooterComposable(
    footerContainer: ModelUIStoreMenuFooter,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .background(MaterialTheme.colorScheme.onPrimary)
            .padding(16.dp),
    ) {
        Text(
            text = footerContainer.name,
            style = MaterialTheme.typography.bodyLarge,
        )
    }
}

@ThemePreviews
@Composable
private fun StoreScreenContainerPreview() {
    HaatTheme {
        StoreScreenContainerBody(testStoreModelUIStoreContainer, "1", {})
    }
}

@ThemePreviews
@Composable
private fun StoreMenuScreenListPreview() {
    HaatTheme {
        StoreMenuScreenList(testStoreListCategory, {})
    }
}

@ThemePreviews
@Composable
private fun StoreSectionHeaderContainerPreview() {
    HaatTheme {
        StoreSectionHeaderContainer(testModelUIStoreInfo, Modifier.fillMaxWidth())
    }
}

@ThemePreviews
@Composable
private fun StoreSubSectionHeaderHighLightListPreview() {
    HaatTheme {
        StoreSubSectionHeaderHighLightList(testModelUIStoreInfo.highLightList)
    }
}

@ThemePreviews
@Composable
private fun StoreSubSectionInfoOfferContainerPreview() {
    HaatTheme {
        StoreSubSectionHeaderOffer(
            R.drawable.icon_delivery,
            "-20% Sale",
            "on part of the items"
        )
    }
}

@ThemePreviews
@Composable
private fun StoreMenuProductsContainerVerticallyPreview() {
    HaatTheme {
        StoreSectionProductsContainer(testModelUIStoreMenuOtherVertical, {},700.dp)
    }
}


@ThemePreviews
@Composable
private fun StoreMenuProductsContainerHorizontallyPreview() {
    HaatTheme {
        StoreSectionProductsContainer(testModelUIStoreProductContainer, {},700.dp)
    }
}

@ThemePreviews
@Composable
private fun StoreMenuProductsVerticallyPreview() {
    HaatTheme {
        StoreMenuProductsVertically(testModelUIStoreProductContainer, {},700.dp)
    }
}

@ThemePreviews
@Composable
private fun StoreMenuProductsContainerPreview() {
    HaatTheme {
        StoreSectionProductsContainer(testModelUIStoreProductContainer, {},700.dp)
    }
}

@ThemePreviews
@Composable
private fun StoreMenuProductsHorizontallyPreview() {
    HaatTheme {
        StoreSectionProductsHorizontally(testModelUIStoreProductContainer, {})
    }
}

@ThemePreviews
@Composable
private fun StoreMenuProductItemCountableNoDiscountInCartPreview() {
    HaatTheme {
        StoreSubSectionProductItem(
            testModelUIStoreProductCountableNoDiscountItemAdded,
            {}, Modifier
                .height(215.dp)
                .width(110.dp)
        )
    }
}

@ThemePreviews
@Composable
private fun StoreMenuProductItemCountableDiscountInCartPreview() {
    HaatTheme {
        StoreSubSectionProductItem(
            testModelUIStoreProductCountableDiscountItemAdded,
            {}, Modifier
                .height(215.dp)
                .width(110.dp)
        )
    }
}

@ThemePreviews
@Composable
private fun StoreMenuProductItemCountableNoDiscountPreview() {
    HaatTheme {
        StoreSubSectionProductItem(
            testModelUIStoreProductCountableNoDiscountItem, {},Modifier
                .height(215.dp)
                .width(110.dp)
        )
    }
}

@ThemePreviews
@Composable
private fun StoreMenuProductItemCountableDiscountPreview() {
    HaatTheme {
        StoreSubSectionProductItem(
            testModelUIStoreProductCountableDiscountItem, {}, Modifier
                .height(215.dp)
                .width(110.dp)
        )
    }
}


@ThemePreviews
@Composable
private fun StoreMenuProductItemWeighAbleNoDiscountInCartPreview() {
    HaatTheme {
        StoreSubSectionProductItem(
            testModelUIStoreProductWeighAbleNoDiscountItemAdded, {}, Modifier
                .height(215.dp)
                .width(110.dp)
        )
    }
}

@ThemePreviews
@Composable
private fun StoreMenuProductItemWeighAbleDiscountInCartMultiplePreview() {
    HaatTheme {
        StoreSubSectionProductItem(
            testModelUIStoreProductWeighAbleDiscountItemAddedMultipleTime, {}, Modifier
                .height(215.dp)
                .width(110.dp)
        )
    }
}

@ThemePreviews
@Composable
private fun StoreMenuProductItemWeighAbleDiscountInCartPreview() {
    HaatTheme {
        StoreSubSectionProductItem(
            testModelUIStoreProductWeighAbleDiscountItemAdded, {}, Modifier
                .height(215.dp)
                .width(110.dp)
        )
    }
}

@ThemePreviews
@Composable
private fun StoreMenuProductItemWeighAbleNoDiscountPreview() {
    HaatTheme {
        StoreSubSectionProductItem(
            testModelUIStoreProductWeighAbleNoDiscountItem, {}, Modifier
                .height(215.dp)
                .width(110.dp)
        )
    }
}

@ThemePreviews
@Composable
private fun StoreMenuProductItemWeighAbleDiscountPreview() {
    HaatTheme {
        StoreSubSectionProductItem(
            testModelUIStoreProductWeighAbleDiscountItem, {}, Modifier
                .height(215.dp)
                .width(110.dp)
        )
    }
}

@ThemePreviews
@Composable
private fun StoreMenuProductItemWeighAbleNoDiscountInfoPreview() {
    HaatTheme {
        StoreSubSectionProductItemInfo(testModelUIStoreProductWeighAbleNoDiscountItemAdded)
    }
}

@ThemePreviews
@Composable
private fun StoreMenuProductItemWeighAbleDiscountInfoPreview() {
    HaatTheme {
        StoreSubSectionProductItemInfo(testModelUIStoreProductWeighAbleDiscountItemAdded)
    }
}

@ThemePreviews
@Composable
private fun QuantitySelectorPreview() {
    HaatTheme {
        QuantitySelector({}, text = "")
    }
}

@ThemePreviews
@Composable
private fun QuantitySelectorAddedPreview() {
    HaatTheme {
        QuantitySelector({}, text = "500g")
    }
}

@ThemePreviews
@Composable
private fun StoreMenuProductItemFooterPreview() {
    HaatTheme {
        StoreSubSectionProductItemFooter()
    }
}

@ThemePreviews
@Composable
private fun StoreMenuCategoryItemPreview() {
    HaatTheme {
        StoreSubSectionMenuCategoryItem(
            testModelUIStoreMenuCategoryItem, modifier = Modifier
                .fillMaxWidth()
                .height(130.dp)
        )
    }
}

@ThemePreviews
@Composable
private fun StoreSectionCategoryContainerPreview() {
    HaatTheme {
        StoreSectionCategoryContainer(testModelUIStoreMenuCategory, 700.dp)
    }
}

@ThemePreviews
@Composable
private fun StoreSectionCategoryVerticallyPreview() {
    HaatTheme {
        StoreSectionCategoryVertically(testModelUIStoreMenuCategory, 700.dp)
    }
}

@ThemePreviews
@Composable
private fun StoreSectionCategoryHorizontallyPreview() {
    HaatTheme {
        StoreSectionCategoryHorizontally(testModelUIStoreMenuCategory)
    }
}

@ThemePreviews
@Composable
private fun StoreNotesComposablePreview() {
    HaatTheme {
        StoreSectionNotesComposable(testModelUIStoreMenuNotes)
    }
}

@ThemePreviews
@Composable
private fun StoreFooterSectionComposablePreview() {
    HaatTheme {
        StoreSectionFooterComposable(testModelUIStoreMenuFooter)
    }
}
