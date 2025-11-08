package dev.reprator.haat.features.restaurant2pane.restaurants.data.remote.modal

import com.fasterxml.jackson.annotation.JsonAlias
import com.fasterxml.jackson.annotation.JsonInclude
import com.fasterxml.jackson.annotation.JsonProperty
import com.fasterxml.jackson.annotation.JsonPropertyOrder

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder(
    "value",
    "ratings",
    "topRated",
    "numberOfRatings"
)
class EntityMenuRating(
    @JsonAlias("ratings")
    @JsonProperty("value")
    val value: String?,
    val topRated: Boolean?,
    val isNew: Boolean?,
    val numberOfRatings: String?)