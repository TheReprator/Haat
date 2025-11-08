package dev.reprator.haat.navigation

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.sizeIn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.NavigationDrawerItem
import androidx.compose.material3.NavigationDrawerItemDefaults
import androidx.compose.material3.NavigationRail
import androidx.compose.material3.NavigationRailItem
import androidx.compose.material3.NavigationRailItemDefaults
import androidx.compose.material3.PermanentDrawerSheet
import androidx.compose.material3.Text
import androidx.compose.material3.adaptive.currentWindowAdaptiveInfo
import androidx.compose.material3.adaptive.currentWindowSize
import androidx.compose.material3.adaptive.navigationsuite.NavigationSuiteScaffoldLayout
import androidx.compose.material3.adaptive.navigationsuite.NavigationSuiteType
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.toSize
import androidx.navigation.NavDestination
import androidx.navigation.NavDestination.Companion.hasRoute
import androidx.window.core.layout.WindowSizeClass
import dev.reprator.haat.navigation.HaatNavigationDefaults.navigationContentColor
import dev.reprator.haat.navigation.HaatNavigationDefaults.navigationIndicatorColor
import dev.reprator.haat.navigation.HaatNavigationDefaults.navigationSelectedItemColor
import kotlinx.coroutines.launch

private fun WindowSizeClass.isCompact() =
    !isWidthAtLeastBreakpoint(WindowSizeClass.WIDTH_DP_MEDIUM_LOWER_BOUND) ||
            !isHeightAtLeastBreakpoint(WindowSizeClass.HEIGHT_DP_MEDIUM_LOWER_BOUND)

@Composable
fun HaatNavigationWrapper(
    currentDestination: NavDestination?,
    navigateToTopLevelDestination: (AppLevelDestination) -> Unit,
    content: @Composable () -> Unit,
) {
    val adaptiveInfo = currentWindowAdaptiveInfo()
    val windowSize = with(LocalDensity.current) {
        currentWindowSize().toSize().toDpSize()
    }

    val navLayoutType = when {
        adaptiveInfo.windowPosture.isTabletop -> NavigationSuiteType.NavigationBar
        adaptiveInfo.windowSizeClass.isCompact() -> NavigationSuiteType.NavigationBar
        adaptiveInfo.windowSizeClass.isWidthAtLeastBreakpoint(WindowSizeClass.WIDTH_DP_EXPANDED_LOWER_BOUND) &&
                windowSize.width >= 1200.dp -> NavigationSuiteType.NavigationDrawer

        else -> NavigationSuiteType.NavigationRail
    }

    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    val coroutineScope = rememberCoroutineScope()

    val gesturesEnabled =
        drawerState.isOpen || navLayoutType == NavigationSuiteType.NavigationRail

    BackHandler(enabled = drawerState.isOpen) {
        coroutineScope.launch {
            drawerState.close()
        }
    }

    ModalNavigationDrawer(
        drawerState = drawerState,
        gesturesEnabled = gesturesEnabled,
        drawerContent = {
            ModalNavigationDrawerContent(
                currentDestination = currentDestination,
                navigateToTopLevelDestination = navigateToTopLevelDestination,
                onDrawerClicked = {
                    coroutineScope.launch {
                        drawerState.close()
                    }
                },
            )
        },
    ) {
        NavigationSuiteScaffoldLayout(
            layoutType = navLayoutType,
            navigationSuite = {
                when (navLayoutType) {
                    NavigationSuiteType.NavigationBar -> HaatBottomNavigationBar(
                        currentDestination = currentDestination,
                        navigateToTopLevelDestination = navigateToTopLevelDestination,
                    )

                    NavigationSuiteType.NavigationRail -> HaatNavigationRail(
                        currentDestination = currentDestination,
                        navigateToTopLevelDestination = navigateToTopLevelDestination,
                        onDrawerClicked = {
                            coroutineScope.launch {
                                drawerState.open()
                            }
                        },
                    )

                    NavigationSuiteType.NavigationDrawer -> PermanentNavigationDrawerContent(
                        currentDestination = currentDestination,
                        navigateToTopLevelDestination = navigateToTopLevelDestination,
                    )
                }
            },
        ) {
            content()
        }
    }
}

@Composable
fun HaatNavigationRail(
    currentDestination: NavDestination?,
    navigateToTopLevelDestination: (AppLevelDestination) -> Unit,
    onDrawerClicked: () -> Unit = {},
) {
    NavigationRail(
        modifier = Modifier.fillMaxHeight(),
        containerColor = MaterialTheme.colorScheme.inverseOnSurface,
    ) {
        Column(
            modifier = Modifier,
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(4.dp),
        ) {
            AppLevelDestination.entries.forEach { haatDestination ->
                NavigationRailItem(
                    selected = currentDestination.hasRoute(haatDestination),
                    onClick = { navigateToTopLevelDestination(haatDestination) },
                    icon = {
                        Icon(
                            painter = painterResource(id = haatDestination.selectedIconId),
                            contentDescription = stringResource(
                                id = haatDestination.iconTextId,
                            ),
                        )
                    },
                    colors = NavigationRailItemDefaults.colors(
                        selectedIconColor = navigationSelectedItemColor(),
                        unselectedIconColor = navigationContentColor(),
                        selectedTextColor = navigationSelectedItemColor(),
                        unselectedTextColor = navigationContentColor(),
                        indicatorColor = navigationIndicatorColor(),
                    ),
                )
            }
        }
    }
}

@Composable
fun HaatBottomNavigationBar(
    currentDestination: NavDestination?,
    navigateToTopLevelDestination: (AppLevelDestination) -> Unit
) {
    NavigationBar(modifier = Modifier.fillMaxWidth()) {
        AppLevelDestination.entries.forEach { haatDestination ->
            val isSelected = currentDestination.hasRoute(haatDestination)
            NavigationBarItem(
                alwaysShowLabel = true,
                selected = currentDestination.hasRoute(haatDestination),
                onClick = { navigateToTopLevelDestination(haatDestination) },
                icon = {
                    Icon(
                        painter = painterResource(id = haatDestination.selectedIconId),
                        contentDescription = stringResource(id = haatDestination.iconTextId),
                    )
                },
                colors = NavigationBarItemDefaults.colors(
                    selectedIconColor = navigationSelectedItemColor(),
                    unselectedIconColor = navigationContentColor(),
                    selectedTextColor = navigationSelectedItemColor(),
                    unselectedTextColor = navigationContentColor(),
                    indicatorColor = navigationIndicatorColor(),
                )
            )
        }
    }
}

@Composable
fun PermanentNavigationDrawerContent(
    currentDestination: NavDestination?,
    navigateToTopLevelDestination: (AppLevelDestination) -> Unit,
) {
    PermanentDrawerSheet(
        modifier = Modifier.sizeIn(minWidth = 200.dp, maxWidth = 300.dp),
        drawerContainerColor = MaterialTheme.colorScheme.surfaceContainerHigh,
    ) {
        Column(
            modifier = Modifier
                .background(MaterialTheme.colorScheme.surfaceContainerHigh)
                .padding(16.dp)
                .verticalScroll(rememberScrollState()),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            AppLevelDestination.entries.forEach { haatDestination ->
                NavigationDrawerItem(
                    selected = currentDestination.hasRoute(haatDestination),
                    label = {
                        Text(
                            text = stringResource(id = haatDestination.iconTextId),
                            modifier = Modifier.padding(horizontal = 16.dp),
                        )
                    },
                    icon = {
                        Icon(
                            painter = painterResource(id = haatDestination.selectedIconId),
                            contentDescription = stringResource(
                                id = haatDestination.iconTextId,
                            ),
                        )
                    },
                    onClick = { navigateToTopLevelDestination(haatDestination) },
                    colors = NavigationDrawerItemDefaults.colors(
                        selectedIconColor = navigationSelectedItemColor(),
                        unselectedIconColor = navigationContentColor(),
                        selectedTextColor = navigationSelectedItemColor(),
                        unselectedTextColor = navigationContentColor(),
                        unselectedContainerColor = Color.Transparent,
                    )
                )
            }
        }
    }
}

@Composable
fun ModalNavigationDrawerContent(
    currentDestination: NavDestination?,
    navigateToTopLevelDestination: (AppLevelDestination) -> Unit,
    onDrawerClicked: () -> Unit = {},
) {
    ModalDrawerSheet {

        Column(
            modifier = Modifier
                .background(MaterialTheme.colorScheme.inverseOnSurface)
                .padding(16.dp)
                .verticalScroll(rememberScrollState()),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            AppLevelDestination.entries.forEach { haatDestination ->
                NavigationDrawerItem(
                    selected = currentDestination.hasRoute(haatDestination),
                    label = {
                        Text(
                            text = stringResource(id = haatDestination.iconTextId),
                            modifier = Modifier.padding(horizontal = 16.dp),
                        )
                    },
                    icon = {
                        Icon(
                            painter = painterResource(id = haatDestination.selectedIconId),
                            contentDescription = stringResource(
                                id = haatDestination.iconTextId,
                            ),
                        )
                    },
                    colors = NavigationDrawerItemDefaults.colors(
                        selectedIconColor = navigationSelectedItemColor(),
                        unselectedIconColor = navigationContentColor(),
                        selectedTextColor = navigationSelectedItemColor(),
                        unselectedTextColor = navigationContentColor(),
                        unselectedContainerColor = Color.Transparent,
                    ),

                    onClick = { navigateToTopLevelDestination(haatDestination) },
                )
            }
        }


    }
}

fun NavDestination?.hasRoute(destination: AppLevelDestination): Boolean =
    this?.hasRoute(route = destination.route) == true



object HaatNavigationDefaults {
    @Composable
    fun navigationContentColor() = MaterialTheme.colorScheme.onSurfaceVariant

    @Composable
    fun navigationSelectedItemColor() = MaterialTheme.colorScheme.onPrimaryContainer

    @Composable
    fun navigationIndicatorColor() = MaterialTheme.colorScheme.primaryContainer
}