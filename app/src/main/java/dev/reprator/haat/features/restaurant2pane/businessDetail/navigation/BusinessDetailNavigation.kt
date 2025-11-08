package dev.reprator.haat.features.restaurant2pane.businessDetail.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptionsBuilder
import androidx.navigation.compose.composable
import androidx.navigation.toRoute
import dev.reprator.haat.features.restaurant2pane.businessDetail.ui.BusinessDetailScreen
import kotlinx.serialization.Serializable

@Serializable data class BusinessDetailRoute(val id: String)

fun NavController.navigateToBusinessDetail(topicId: String, navOptions: NavOptionsBuilder.() -> Unit = {}) {
    navigate(route = BusinessDetailRoute(topicId)) {
        navOptions()
    }
}

fun NavGraphBuilder.businessDetailSection(
    showBackButton: Boolean,
    onBackClick: () -> Unit,
) {
    composable<BusinessDetailRoute> { entry ->
        val id = entry.toRoute<BusinessDetailRoute>().id
        BusinessDetailScreen(showBackButton, onBackClick)
    }
}
