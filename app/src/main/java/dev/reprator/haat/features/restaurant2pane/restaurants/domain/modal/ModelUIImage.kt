package dev.reprator.haat.features.restaurant2pane.restaurants.domain.modal

data class ModelUIImage(val imageUrl: String, val blurHash: String?) {
    companion object {
        val initial = ModelUIImage("", "")
    }
}