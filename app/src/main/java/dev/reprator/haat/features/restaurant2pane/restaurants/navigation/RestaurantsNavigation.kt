package dev.reprator.haat.features.restaurant2pane.restaurants.navigation

import androidx.navigation.NavController
import androidx.navigation.NavOptions
import kotlinx.serialization.Serializable

@Serializable
data object RestaurantsRoute

fun NavController.navigateToRestaurants(navOptions: NavOptions) =
    navigate(route = RestaurantsRoute, navOptions)
