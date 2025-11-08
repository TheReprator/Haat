package dev.reprator.haat.features.restaurant2pane.restaurants.data.remote.modal

import com.fasterxml.jackson.annotation.JsonAlias
import com.fasterxml.jackson.annotation.JsonInclude
import com.fasterxml.jackson.annotation.JsonPropertyOrder

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder(
    "ar",
    "en-US",
    "he",
    "fr"
)
class EntityMenuBusinessName(
    val ar: String?, val he: String?,
    @JsonAlias("en-US")
    val enUS: String?, val fr: String?
)