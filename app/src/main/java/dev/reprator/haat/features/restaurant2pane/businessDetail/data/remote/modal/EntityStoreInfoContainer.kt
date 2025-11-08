package dev.reprator.haat.features.restaurant2pane.businessDetail.data.remote.modal


import com.fasterxml.jackson.annotation.JsonInclude
import com.fasterxml.jackson.annotation.JsonPropertyOrder
import dev.reprator.haat.features.restaurant2pane.restaurants.data.remote.modal.EntityMenuAddress
import dev.reprator.haat.features.restaurant2pane.restaurants.data.remote.modal.EntityMenuBusinessName
import dev.reprator.haat.features.restaurant2pane.restaurants.data.remote.modal.EntityMenuImages
import dev.reprator.haat.features.restaurant2pane.restaurants.data.remote.modal.EntityMenuRating


@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder(
    "id",
    "rating",
    "location",
    "status",
    "name",
    "shareData",
    "iconImage",
    "bannerImages",
    "currency",
    "notes",
    "workingHours"
)
class EntityStoreInfoContainer(
    val id: String?,
    val rating: EntityMenuRating?,
    val location: EntityMenuAddress?,
    val status: EntityStoreStatus?,
    val name: EntityMenuBusinessName?,
    val iconImage: EntityMenuImages?,
    val bannerImages: List<EntityMenuImages>?,
    val currency: EntityStoreCurrency?,
    val notes: List<EntityStoreNotes>?,
    val workingHours: EntityStoreInfoWorkingHours?
)

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder(
    "isOpened24Hours",
    "isClosed24Hours",
    "currentWorkingHour",
    "workingHours"
)
class EntityStoreInfoWorkingHours(
    val isOpened24Hours: Boolean?,
    val isClosed24Hours: Boolean?,
    val currentWorkingHour: EntityStoreWorkingHourItem?,
    val workingHours: List<EntityStoreWorkingHourItem>?,
)

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder(
    "dayOfWeek",
    "fromHour",
    "toHour",
    "type",
)
class EntityStoreWorkingHourItem(
    val dayOfWeek: Int?,
    val fromHour: Int?,
    val toHour: Int?,
    val type: Int?,
)

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder(
    "status",
    "closestWorkingHour",
    "is24Hours"
)
class EntityStoreStatus(
    val status: Int?,
    val is24Hours: Boolean?,
)

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder(
    "symbol",
    "name"
)
class EntityStoreCurrency(
    val symbol: String?,
    val name: String?,
)


@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder(
    "hide",
    "timeout",
    "type"
)
class EntityStoreNotes(
    val text: String?,
    val type: String?,
)