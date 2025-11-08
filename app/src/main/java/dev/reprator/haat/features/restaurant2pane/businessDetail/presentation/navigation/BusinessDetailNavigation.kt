package dev.reprator.haat.features.restaurant2pane.businessDetail.presentation.navigation

import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptionsBuilder
import androidx.navigation.compose.composable
import androidx.navigation.toRoute
import dev.reprator.haat.features.restaurant2pane.businessDetail.presentation.StoreInfoViewModel
import dev.reprator.haat.features.restaurant2pane.businessDetail.presentation.ui.StoreInfoScreen
import kotlinx.serialization.Serializable

@Serializable
data class BusinessDetailRoute(val storeId: String)

fun NavController.navigateToBusinessDetail(
    topicId: String,
    navOptions: NavOptionsBuilder.() -> Unit = {}
) {
    navigate(route = BusinessDetailRoute(topicId)) {
        navOptions()
    }
}

fun NavGraphBuilder.businessDetailSection(
    showBackButton: Boolean,
    onBackClick: () -> Unit,
) {
    composable<BusinessDetailRoute> { entry ->
        val id = entry.toRoute<BusinessDetailRoute>().storeId
        StoreInfoScreen(
            showBackButton = showBackButton,
            onBackClick = onBackClick,
            viewModel = hiltViewModel<StoreInfoViewModel, StoreInfoViewModel.Factory>(
                key = id,
            ) { factory ->
                factory.create(id)
            },
        )
    }
}
