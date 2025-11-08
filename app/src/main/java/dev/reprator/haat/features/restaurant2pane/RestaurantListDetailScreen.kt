package dev.reprator.haat.features.restaurant2pane

import androidx.activity.compose.BackHandler
import androidx.compose.animation.AnimatedContent
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.material3.LocalMinimumInteractiveComponentSize
import androidx.compose.material3.VerticalDragHandle
import androidx.compose.material3.adaptive.ExperimentalMaterial3AdaptiveApi
import androidx.compose.material3.adaptive.WindowAdaptiveInfo
import androidx.compose.material3.adaptive.currentWindowAdaptiveInfo
import androidx.compose.material3.adaptive.layout.AnimatedPane
import androidx.compose.material3.adaptive.layout.ListDetailPaneScaffoldRole
import androidx.compose.material3.adaptive.layout.PaneAdaptedValue
import androidx.compose.material3.adaptive.layout.PaneExpansionAnchor
import androidx.compose.material3.adaptive.layout.ThreePaneScaffoldDestinationItem
import androidx.compose.material3.adaptive.layout.calculatePaneScaffoldDirective
import androidx.compose.material3.adaptive.layout.rememberPaneExpansionState
import androidx.compose.material3.adaptive.navigation.BackNavigationBehavior
import androidx.compose.material3.adaptive.navigation.NavigableListDetailPaneScaffold
import androidx.compose.material3.adaptive.navigation.ThreePaneScaffoldNavigator
import androidx.compose.material3.adaptive.navigation.ThreePaneScaffoldPredictiveBackHandler
import androidx.compose.material3.adaptive.navigation.rememberListDetailPaneScaffoldNavigator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clipToBounds
import androidx.compose.ui.layout.layout
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import dev.reprator.haat.features.restaurant2pane.businessDetail.presentation.StoreInfoViewModel
import dev.reprator.haat.features.restaurant2pane.businessDetail.presentation.navigation.BusinessDetailRoute
import dev.reprator.haat.features.restaurant2pane.businessDetail.presentation.ui.BusinessDetailPlaceholder
import dev.reprator.haat.features.restaurant2pane.businessDetail.presentation.ui.StoreInfoScreen
import dev.reprator.haat.features.restaurant2pane.restaurants.presentation.navigation.RestaurantsRoute
import dev.reprator.haat.features.restaurant2pane.restaurants.presentation.ui.RestaurantScreen
import kotlinx.coroutines.launch
import kotlinx.serialization.Serializable
import kotlin.math.max

@Serializable internal object RestaurantPlaceholderRoute

fun NavController.navigateToRestaurantListDetailScreen(navOptions: NavOptions) = navigate(route = RestaurantsRoute, navOptions)

fun NavGraphBuilder.restaurantListDetailScreen() {
    composable<RestaurantsRoute> {
        RestaurantListDetailScreen()
    }
}

@Composable
internal fun RestaurantListDetailScreen(
    viewModel: Restaurant2PaneViewModel = hiltViewModel(),
    windowAdaptiveInfo: WindowAdaptiveInfo = currentWindowAdaptiveInfo(),
) {
    val selectedRestaurantID by viewModel.selectedTopicId.collectAsStateWithLifecycle()
    RestaurantListDetailScreen(
        selectedRestaurantID = selectedRestaurantID,
        onRestaurantClick = viewModel::onTopicClick,
        windowAdaptiveInfo = windowAdaptiveInfo,
    )
}

@OptIn(ExperimentalMaterial3AdaptiveApi::class)
@Composable
internal fun RestaurantListDetailScreen(
    selectedRestaurantID: String?,
    onRestaurantClick: (String) -> Unit,
    windowAdaptiveInfo: WindowAdaptiveInfo,
) {
    val listDetailNavigator = rememberListDetailPaneScaffoldNavigator(
        scaffoldDirective = calculatePaneScaffoldDirective(windowAdaptiveInfo),
        initialDestinationHistory = listOfNotNull(
            ThreePaneScaffoldDestinationItem(ListDetailPaneScaffoldRole.List),
            ThreePaneScaffoldDestinationItem<Nothing>(ListDetailPaneScaffoldRole.Detail).takeIf {
                selectedRestaurantID != null
            },
        ),
    )
    val coroutineScope = rememberCoroutineScope()

    val paneExpansionState = rememberPaneExpansionState(
        anchors = listOf(
            PaneExpansionAnchor.Proportion(0f),
            PaneExpansionAnchor.Proportion(0.5f),
            PaneExpansionAnchor.Proportion(1f),
        ),
    )

    ThreePaneScaffoldPredictiveBackHandler(
        listDetailNavigator,
        BackNavigationBehavior.PopUntilScaffoldValueChange,
    )
    BackHandler(
        paneExpansionState.currentAnchor == PaneExpansionAnchor.Proportion(0f) &&
            listDetailNavigator.isListPaneVisible() &&
            listDetailNavigator.isDetailPaneVisible(),
    ) {
        coroutineScope.launch {
            paneExpansionState.animateTo(PaneExpansionAnchor.Proportion(1f))
        }
    }

    var restaurantRoute by remember {
        val route = selectedRestaurantID?.let { BusinessDetailRoute(storeId = it) } ?: RestaurantPlaceholderRoute
        mutableStateOf(route)
    }

    fun onStoreClickShowDetailPane(selectedRestaurantID: String) {
        onRestaurantClick(selectedRestaurantID)
        restaurantRoute = BusinessDetailRoute(storeId = selectedRestaurantID)
        coroutineScope.launch {
            listDetailNavigator.navigateTo(ListDetailPaneScaffoldRole.Detail)
        }
        if (paneExpansionState.currentAnchor == PaneExpansionAnchor.Proportion(1f)) {
            coroutineScope.launch {
                paneExpansionState.animateTo(PaneExpansionAnchor.Proportion(0f))
            }
        }
    }

    val mutableInteractionSource = remember { MutableInteractionSource() }
    val minPaneWidth = 300.dp

    NavigableListDetailPaneScaffold(
        navigator = listDetailNavigator,
        listPane = {
            AnimatedPane {
                Box(
                    modifier = Modifier.clipToBounds()
                        .layout { measurable, constraints ->
                            val width = max(minPaneWidth.roundToPx(), constraints.maxWidth)
                            val placeable = measurable.measure(
                                constraints.copy(
                                    minWidth = minPaneWidth.roundToPx(),
                                    maxWidth = width,
                                ),
                            )
                            layout(constraints.maxWidth, placeable.height) {
                                placeable.placeRelative(
                                    x = 0,
                                    y = 0,
                                )
                            }
                        },
                ) {
                    RestaurantScreen(
                        onBusinessClick =::onStoreClickShowDetailPane,
                        shouldHighlightSelectedBusiness = listDetailNavigator.isDetailPaneVisible())
                }
            }
        },
        detailPane = {
            AnimatedPane {
                Box(
                    modifier = Modifier.clipToBounds()
                        .layout { measurable, constraints ->
                            val width = max(minPaneWidth.roundToPx(), constraints.maxWidth)
                            val placeable = measurable.measure(
                                constraints.copy(
                                    minWidth = minPaneWidth.roundToPx(),
                                    maxWidth = width,
                                ),
                            )
                            layout(constraints.maxWidth, placeable.height) {
                                placeable.placeRelative(
                                    x = constraints.maxWidth -
                                        max(constraints.maxWidth, placeable.width),
                                    y = 0,
                                )
                            }
                        },
                ) {
                    AnimatedContent(restaurantRoute) { route ->
                        when (route) {
                            is BusinessDetailRoute -> {
                                StoreInfoScreen(
                                    showBackButton = !listDetailNavigator.isListPaneVisible(),
                                    onBackClick = {
                                        coroutineScope.launch {
                                            listDetailNavigator.navigateBack()
                                        }
                                    },
                                    viewModel = hiltViewModel<StoreInfoViewModel, StoreInfoViewModel.Factory>(
                                        key = route.storeId,
                                    ) { factory ->
                                        factory.create(route.storeId)
                                    },
                                )
                            }
                            is RestaurantPlaceholderRoute -> {
                                BusinessDetailPlaceholder()
                            }
                        }
                    }
                }
            }
        },
        paneExpansionState = paneExpansionState,
        paneExpansionDragHandle = {
            VerticalDragHandle(
                modifier = Modifier.paneExpansionDraggable(
                    state = paneExpansionState,
                    minTouchTargetSize = LocalMinimumInteractiveComponentSize.current,
                    interactionSource = mutableInteractionSource
                ),
                interactionSource = mutableInteractionSource,
            )
        },
    )
}

@OptIn(ExperimentalMaterial3AdaptiveApi::class)
private fun <T> ThreePaneScaffoldNavigator<T>.isListPaneVisible(): Boolean =
    scaffoldValue[ListDetailPaneScaffoldRole.List] == PaneAdaptedValue.Expanded

@OptIn(ExperimentalMaterial3AdaptiveApi::class)
private fun <T> ThreePaneScaffoldNavigator<T>.isDetailPaneVisible(): Boolean =
    scaffoldValue[ListDetailPaneScaffoldRole.Detail] == PaneAdaptedValue.Expanded
