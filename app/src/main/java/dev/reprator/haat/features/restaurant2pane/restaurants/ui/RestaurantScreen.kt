package dev.reprator.haat.features.restaurant2pane.restaurants.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.border
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
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PagerDefaults
import androidx.compose.foundation.pager.PagerSnapDistance
import androidx.compose.foundation.pager.PagerState
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import dev.reprator.haat.R
import dev.reprator.haat.features.restaurant2pane.restaurants.ModelUIBanner
import dev.reprator.haat.features.restaurant2pane.restaurants.ModelUIBannerContainer
import dev.reprator.haat.features.restaurant2pane.restaurants.ModelUITag
import dev.reprator.haat.features.restaurant2pane.restaurants.ModelUITagContainer
import dev.reprator.haat.features.restaurant2pane.restaurants.testBannerContainer
import dev.reprator.haat.features.restaurant2pane.restaurants.testTagContainer
import dev.reprator.haat.ui.ThemePreviews
import dev.reprator.haat.ui.components.DynamicAsyncImage
import dev.reprator.haat.ui.components.indicator.PagerWormIndicator
import dev.reprator.haat.ui.theme.HaatTheme
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@Composable
fun RestaurantScreen(
    onBusinessClick: (String) -> Unit,
    modifier: Modifier = Modifier,
    shouldHighlightSelectedBusiness: Boolean = false,
) {
    Column(
        modifier = modifier
            .fillMaxSize()
    ) {
        HomeTopBar("DubaisfsdfsdfsdfsdfsdwerwrewrwerewrSinghVikram", {}, {})

        Spacer(modifier = Modifier.height(8.dp))

        BannerPagerContainer(testBannerContainer)

        Spacer(modifier = Modifier.height(8.dp))

        CategoryTagContainer(testTagContainer)
    }
}

@Composable
private fun HomeTopBar(
    userLocation: String,
    onMenuClick: () -> Unit,
    onSearchClick: () -> Unit
) {
    Row(
        modifier = Modifier
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
private fun BannerPagerContainer(bannerContainer: ModelUIBannerContainer) {

    val pagerState = rememberPagerState(pageCount = { bannerContainer.imageBannerList.size })

    val fling = PagerDefaults.flingBehavior(
        state = pagerState,
        pagerSnapDistance = PagerSnapDistance.atMost(5)
    )

    LaunchedEffect(key1 = pagerState.currentPage) {
        launch {
            delay(bannerContainer.interval * 1000)
            var nextPage = pagerState.currentPage + 1
            if (nextPage >= pagerState.pageCount) {
                nextPage = 0
            }
            pagerState.animateScrollToPage(nextPage)
        }
    }

    BannerPager(bannerContainer.imageBannerList, pagerState)
}


@Composable
private fun BannerPager(
    imageBannerList: List<ModelUIBanner>,
    pagerState: PagerState = rememberPagerState(pageCount = { imageBannerList.size } )
) {
    Box(contentAlignment = Alignment.Center) {
        HorizontalPager(
            state = pagerState,
            modifier = Modifier
                .fillMaxWidth()
                .height(200.dp),
            pageSpacing = 8.dp,
            key = { index ->
                imageBannerList[index].id
            }
        ) { page ->
            val banner = imageBannerList[page]
            Card(modifier = Modifier.fillMaxSize()) {
                DynamicAsyncImage(
                    imageUrl = "https://im-staging.haat.delivery/${banner.image.imageUrl}",
                    contentDescription = null,
                    blurHash = banner.image.blurHash,
                    modifier = Modifier.fillMaxSize(),
                )
            }
        }

        PagerWormIndicator(
            pageCount = pagerState.pageCount,
            currentPageFraction = remember {
                derivedStateOf { pagerState.currentPage + pagerState.currentPageOffsetFraction }
            },
            activeDotColor = MaterialTheme.colorScheme.primary,
            activeDotSize = 12.dp,
            minDotSize = 4.dp,
            dotColor = MaterialTheme.colorScheme.outline,
        )
    }
}

@Composable
private fun CategoryTagContainer(modelUITagContainer: ModelUITagContainer) {
    val state: LazyListState = rememberLazyListState()
    Column(modifier = Modifier.padding(horizontal = 16.dp)) {
        Text(text = modelUITagContainer.name, style = MaterialTheme.typography.titleMedium)
        Spacer(modifier = Modifier.height(8.dp))
        CategoryTags(modelUITagContainer.imageList, state)
        Spacer(modifier = Modifier.height(8.dp))
    }
}

@Composable
private fun CategoryTags(tagsData: List<ModelUITag>, state: LazyListState = rememberLazyListState()) {
    LazyRow(
        modifier = Modifier.padding(16.dp),
        state = state
    ) {
        items(tagsData.size, key = { index ->
            tagsData[index].id
        }) { index ->
            CategoryTagItem(tagsData[index])
        }
    }
}

@Composable
private fun CategoryTagItem(tag: ModelUITag) {
    Column(
        modifier = Modifier.padding(16.dp)
    ) {

        DynamicAsyncImage(
            imageUrl = "https://im-staging.haat.delivery/${tag.image.imageUrl}",
            contentDescription = null,
            blurHash = tag.image.blurHash,
            modifier = Modifier
                .width(88.dp)
                .height(88.dp)
        )
        Text(
            text = tag.name,
            style = MaterialTheme.typography.titleSmall,
            modifier = Modifier
                .padding(horizontal = 8.dp)
        )

    }
}


@ThemePreviews
@Composable
private fun HomeBannerPreview() {

    HaatTheme {
        BannerPagerContainer(testBannerContainer)
    }
}

@ThemePreviews
@Composable
private fun HomeTagsPreview() {
    HaatTheme {
        CategoryTagContainer(testTagContainer)
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

