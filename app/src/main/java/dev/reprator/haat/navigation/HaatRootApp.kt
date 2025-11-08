package dev.reprator.haat.navigation

import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import dev.reprator.haat.features.comingSoon.navigation.RouteOthers
import dev.reprator.haat.features.comingSoon.navigation.navigateToComingSoon
import dev.reprator.haat.features.restaurant2pane.restaurantListDetailScreen
import dev.reprator.haat.features.restaurant2pane.restaurants.presentation.navigation.RestaurantsRoute


@Composable
fun HaatRootApp() {
    val navController = rememberNavController()
    val navigationActions = remember(navController) {
        HaatNavigationActions(navController)
    }
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentDestination = navBackStackEntry?.destination

    Surface {
        HaatNavigationWrapper(
            currentDestination = currentDestination,
            navigateToTopLevelDestination = navigationActions::navigateTo,
        ) {
            HaatNavHost(navController = navController)
        }
    }
}

@Composable
fun HaatNavHost(
    navController: NavHostController,
    modifier: Modifier = Modifier,
) {
    NavHost(
        navController = navController,
        startDestination = RestaurantsRoute,
        modifier = modifier,
    ) {
       restaurantListDetailScreen()

        navigateToComingSoon<RouteOthers.Orders>()
        navigateToComingSoon<RouteOthers.Profile>()
        navigateToComingSoon<RouteOthers.Market>()
        navigateToComingSoon<RouteOthers.Cart>()
    }
}
