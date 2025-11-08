package dev.reprator.haat.features.restaurant2pane.restaurants.data.remote.modal

import com.fasterxml.jackson.annotation.JsonAlias
import com.fasterxml.jackson.annotation.JsonInclude
import com.fasterxml.jackson.annotation.JsonProperty
import com.fasterxml.jackson.annotation.JsonPropertyOrder

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder(
    "serverImage",
    "smallServerImage",
    "blurhashImage"
)
class EntityMenuImages(
    @JsonAlias("serverImageUrl")
    @JsonProperty("serverImage")
    val serverImage: String?,
    @JsonAlias("blurhashImage")
    @JsonProperty("blurhash")
    val blurhashImage: String?)
