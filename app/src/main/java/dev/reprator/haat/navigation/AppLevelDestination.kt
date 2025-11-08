package dev.reprator.haat.navigation

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.navOptions
import dev.reprator.haat.R
import dev.reprator.haat.features.comingSoon.navigation.RouteOthers
import dev.reprator.haat.features.comingSoon.navigation.navigateToComingSoon
import dev.reprator.haat.features.restaurant2pane.restaurants.navigation.RestaurantsRoute
import dev.reprator.haat.features.restaurant2pane.restaurants.navigation.navigateToRestaurants
import kotlin.reflect.KClass

enum class AppLevelDestination(
    @DrawableRes val selectedIconId: Int,
    @StringRes val iconTextId: Int,
    val route: KClass<*>,
) {
    RESTAURANTS(
        R.drawable.icon_nav_restaurants,
        iconTextId = R.string.nav_restaurant,
        route = RestaurantsRoute::class,
    ),
    MARKET(
        R.drawable.icon_nav_market,
        iconTextId = R.string.nav_market,
        route = RouteOthers.Market::class,
    ),
    CART(
        R.drawable.icon_nav_cart,
        iconTextId = R.string.nav_cart,
        route = RouteOthers.Cart::class,
    ),
    ORDERS(
        R.drawable.icon_nav_order,
        iconTextId = R.string.nav_orders,
        route = RouteOthers.Orders::class,
    ),
    PROFILE(
        R.drawable.icon_nav_profile,
        iconTextId = R.string.nav_profile,
        route = RouteOthers.Profile::class,
    ),
}


class HaatNavigationActions(private val navController: NavHostController) {

    fun navigateTo(destination: AppLevelDestination) {
        val topLevelNavOptions = navOptions {
            // Pop up to the start destination of the graph to
            // avoid building up a large stack of destinations
            // on the back stack as users select items
            popUpTo(navController.graph.findStartDestination().id) {
                saveState = true
            }
            // Avoid multiple copies of the same destination when
            // reselecting the same item
            launchSingleTop = true
            // Restore state when reselecting a previously selected item
            restoreState = true
        }

        when (destination) {
            AppLevelDestination.RESTAURANTS -> navController.navigateToRestaurants(topLevelNavOptions)
            AppLevelDestination.MARKET -> navController.navigateToComingSoon(topLevelNavOptions, RouteOthers.Market)
            AppLevelDestination.PROFILE -> navController.navigateToComingSoon(topLevelNavOptions, RouteOthers.Profile)
            AppLevelDestination.CART -> navController.navigateToComingSoon(topLevelNavOptions, RouteOthers.Cart)
            AppLevelDestination.ORDERS -> navController.navigateToComingSoon(topLevelNavOptions, RouteOthers.Orders)
        }
    }
}