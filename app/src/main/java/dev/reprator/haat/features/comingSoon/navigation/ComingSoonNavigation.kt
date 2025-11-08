package dev.reprator.haat.features.comingSoon.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import dev.reprator.haat.features.comingSoon.ui.ComingSoonScreen
import kotlinx.serialization.Serializable

sealed interface RouteOthers {
    @Serializable
    data object Market : RouteOthers
    @Serializable
    data object Cart : RouteOthers
    @Serializable
    data object Orders : RouteOthers
    @Serializable
    data object Profile : RouteOthers
}

fun NavController.navigateToComingSoon(navOptions: NavOptions, route: RouteOthers) =
    navigate(route = route, navOptions)


inline fun <reified T: RouteOthers> NavGraphBuilder.navigateToComingSoon() {
    composable<T> {
        ComingSoonScreen()
    }
}
