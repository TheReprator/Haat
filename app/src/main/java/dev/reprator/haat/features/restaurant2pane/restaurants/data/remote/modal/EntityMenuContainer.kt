package dev.reprator.haat.features.restaurant2pane.restaurants.data.remote.modal

import com.fasterxml.jackson.annotation.JsonAlias
import com.fasterxml.jackson.annotation.JsonInclude
import com.fasterxml.jackson.annotation.JsonPropertyOrder

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder(
    "tags",
    "mainPageBanners",
    "categories",
    "userAddresses"
)
class EntityMenuContainer(
    val tags: EntityMenuTagContainer?,
    val mainPageBanners: EntityMenuMainPageBanners?,
    val categories: List<EntityMenuCategory>?,
    val userAddresses: EntityMenuAddresses?
)


@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder(
    "interval",
    "banners"
)
class EntityMenuMainPageBanners(
    val interval: Int?,
    val banners: List<EntityMenuBanner> = emptyList()
)

@JsonPropertyOrder(
    "id",
    "name",
    "images",
    "backgroundColor"
)
class EntityMenuTag(
    val id: String?, val name: String?, val images: EntityMenuImages?,
    val backgroundColor: String?
)

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder(
    "title",
    "tags"
)
class EntityMenuTagContainer(val title: String?, val tags: List<EntityMenuTag>)


@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder(
    "businessId",
    "businessName",
    "businessType",
    "linkedBanner",
    "image"
)
class EntityMenuBanner(
    val businessId: String?,
    val businessName: EntityMenuBusinessName?, val businessType: String?,
    val image: EntityMenuImages
)

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder(
    "id",
    "priority",
    "elementType",
    "isPopular",
    "name",
    "backgroundImage",
    "topImage",
    "topImageType",
    "isSponsored",
    "viewAll",
    "backgroundColor",
    "stores",
    "image",
    "businessId",
    "businessType",
    "openOutsideApp",
    "url",
    "title",
    "entityId",
    "maxRows"
)
class EntityMenuCategory(
    val id: String?,val priority: Int?, val elementType: String?,
    val isPopular: Boolean?, val name: String?, val backgroundImage: EntityMenuImages?,
    val topImage: EntityMenuImages?, val topImageType: Int?,
    val isSponsored: Boolean?, val backgroundColor: String?, val stores: List<EntityMenuStore>?,
    val image: EntityMenuImages?, val businessId: String?, val entityId: String?, val title: String?
)

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder(
    "storeId",
    "franchiseId",
    "name",
    "address",
    "opacity",
    "distance",
    "icon",
    "rating",
    "labels",
    "closestWorkingHour",
    "is24Hours",
    "priority",
    "status",
    "isNew",
    "zoneId",
    "type",
    "businessCardImage",
    "cardImageType",
    "backgroundImage",
    "foregroundImage",
    "title",
    "subTitle"
)
class EntityMenuStore(
    val storeId: String?, val franchiseId: String?,
    val name: String?, val address: String?, val opacity: Int?,
    val icon: EntityMenuImages?, val rating: EntityMenuRating?,
    val closestWorkingHour: String?, val is24Hours: Boolean?, val status: Int?, val isNew: Boolean?,
    val businessCardImage: EntityMenuImages?, val cardImageType: String?,
    val backgroundImage: EntityMenuImages?, val foregroundImage: List<EntityMenuImages>?,
    val title: String?, val subTitle: String?
)

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder(
    "addresses",
    "addressHintMessage",
    "showDisruptionMessage",
)
class EntityMenuAddresses(
    val addressHintMessage: String?,
    val showDisruptionMessage: Boolean?,
    val addresses: List<EntityMenuAddress>?
)