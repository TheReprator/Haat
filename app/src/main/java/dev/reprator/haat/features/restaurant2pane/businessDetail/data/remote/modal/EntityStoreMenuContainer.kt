package dev.reprator.haat.features.restaurant2pane.businessDetail.data.remote.modal

import com.fasterxml.jackson.annotation.JsonInclude
import com.fasterxml.jackson.annotation.JsonPropertyOrder
import dev.reprator.haat.features.restaurant2pane.restaurants.data.remote.modal.EntityMenuBusinessName
import dev.reprator.haat.features.restaurant2pane.restaurants.data.remote.modal.EntityMenuImages

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder(
    "sections"
)
class EntityStoreMenuContainer(
    val sections: List<EntityStoreSectionContainer>?
)

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder(
    "id",
    "type",
    "name",
    "categories",
    "items",
    "orientation"
)
class EntityStoreSectionContainer(
    val id: String?,
    val type: String?,
    val name: String?,
    val orientation: String?,
    val categories: List<EntityStoreSectionCategoryItem>?,
    val items: List<EntityStoreProductItem>?
)


@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder(
    "id",
    "name",
    "image"
)
class EntityStoreSectionCategoryItem(
    val id: String?,
    val name: String?,
    val image: EntityMenuImages?
)


@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder(
    "name",
    "description",
    "categoryName",
    "categoryId",
    "subCategoryId",
    "orientation",
    "unitType",
    "unitStep",
    "marketCategoryId",
    "marketSubCategoryId",
    "quantityType",
    "unitDetails",
    "shareData",
    "id",
    "basePrice",
    "discountPercentage",
    "notAvailable",
    "productImages",
    "productCode",
    "pricePerWeight",
    "avgWeightPerItem",
    "weightToPresent",
)
class EntityStoreProductItem(
    val name: EntityMenuBusinessName?,
    val description: EntityMenuImages?,
    val categoryId: Int?,
    val categoryName: EntityMenuBusinessName?,
    val subCategoryId: Int?,
    val orientation: String?,
    val unitType: Int?,
    val minUnit: Int?,
    val maxUnit: Int?,
    val unitStep: Int?,
    val marketCategoryId: Int?,
    val marketSubCategoryId: Int?,
    val quantityType: String?,
    val unitDetails: EntityStoreUnitDetails?,
    val id: Int?,
    val basePrice: Double?,
    val discountPercentage: Int?,
    val discountPrice: Double?,
    val notAvailable: Boolean?,
    val productImages: List<EntityMenuImages>?,
    val productCode: String?,
    val pricePerWeight: Int?,
    val avgWeightPerItem: String?,
    val weightToPresent: String?,
)


@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder(
    "stepSize",
    "unitType",
)
class EntityStoreUnitDetails(
    val stepSize: String?,
    val unitType: String?,
)
