package dev.reprator.haat.features.restaurant2pane.restaurants.data.remote.modal

import com.fasterxml.jackson.annotation.JsonAlias
import com.fasterxml.jackson.annotation.JsonInclude
import com.fasterxml.jackson.annotation.JsonProperty
import com.fasterxml.jackson.annotation.JsonPropertyOrder

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder(
    "id",
    "areaId",
    "longitude",
    "latitude",
    "locationName",
    "city",
    "type",
    "areaDescriptor",
    "street",
    "apartment",
    "buildingNumber",
    "buildingName",
    "floor",
    "driverInstructions",
    "rawStreetName",
    "rawCityName",
    "title",
    "subTitle",
    "streetToDisplay",
    "lastUsageDate",
    "address"
)
class EntityMenuAddress(
    @JsonAlias("areaId")
    @JsonProperty("id")
    val id: String?,
    val longitude: Double?,
    val latitude: Double?, val city: String?,
    val address: String?,
    val title: String?, val subTitle: String?
)