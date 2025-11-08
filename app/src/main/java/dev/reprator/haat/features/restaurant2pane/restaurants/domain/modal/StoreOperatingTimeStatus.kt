package dev.reprator.haat.features.restaurant2pane.restaurants.domain.modal

enum class StoreOperatingTimeStatus(val status: Int, val stringId: String) {
    OPEN(0, "Open"),
    OPENING_SOON(1, "Opening Soon"),
    CLOSED(2, "Closed"),
    CLOSING_SOON(3, "Closing Soon"),
    BUSY(4, "Busy"),
    SOON(5, "Soon"),
    TEMPORARILY_CLOSED(6, "Temporarily Closed"),
    CLOSED_UNTIL_TOMORROW(7, "Closed Until Tomorrow");

    companion object {
        public fun Int.convertToStoreOperatingTimeStatus(): StoreOperatingTimeStatus = entries.firstOrNull { it.status == this } ?: OPENING_SOON
    }
}
