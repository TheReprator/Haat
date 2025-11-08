package dev.reprator.haat.features.restaurant2pane

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.navigation.toRoute
import dagger.hilt.android.lifecycle.HiltViewModel
import dev.reprator.haat.features.restaurant2pane.restaurants.presentation.navigation.RestaurantsRoute
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

const val RESTAURANT_ID_KEY = "selectedRestaurantID"

@HiltViewModel
class Restaurant2PaneViewModel @Inject constructor(
    private val savedStateHandle: SavedStateHandle,
) : ViewModel() {

    val route = savedStateHandle.toRoute<RestaurantsRoute>()
    val selectedTopicId: StateFlow<String?> = savedStateHandle.getStateFlow(
        key = RESTAURANT_ID_KEY,
        initialValue = null,
    )

    fun onTopicClick(topicId: String?) {
        savedStateHandle[RESTAURANT_ID_KEY] = topicId
    }
}
