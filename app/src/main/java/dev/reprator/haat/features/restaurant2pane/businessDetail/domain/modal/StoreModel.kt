package dev.reprator.haat.features.restaurant2pane.businessDetail.domain.modal

import androidx.annotation.DrawableRes
import dev.reprator.haat.R
import dev.reprator.haat.features.restaurant2pane.businessDetail.domain.modal.ModelUIStoreProductItem.Companion.CURRENCY_SYMBOL
import dev.reprator.haat.features.restaurant2pane.businessDetail.domain.modal.StoreMenuItemQuantityWeighable.UnitDetails
import dev.reprator.haat.features.restaurant2pane.restaurants.domain.modal.ModelUIImage
import java.util.Locale

data class ModelUIStoreContainer(
    val headerInfo: ModelUIHeaderInfo, val storeCategoryList: List<StoreListCategory>,
    val orderInfo: ModelUiOrderInfoContainer = ModelUiOrderInfoContainer()) {
        companion object {
            val initial = ModelUIStoreContainer(ModelUIHeaderInfo.initial, emptyList())
        }
}

data class ModelUIHeaderInfo(
    val bannerImage: ModelUIImage,
    val icon: ModelUIImage,
    val categoryList: List<ModelUIProductCategoryItem>) {
    companion object {

        val initial = ModelUIHeaderInfo(ModelUIImage.initial, ModelUIImage.initial, emptyList())
    }
}

data class ModelUIStoreInfo(
    val name: String,
    val address: String,
    val icon: ModelUIImage,
    val time: ModelUIStoreInfoTiming,
    val highLightList: List<ModelUIStoreInfoHighlight>,
    override val category: StoreCategory = StoreCategory.HEADER_INFO,
    override val id: String = category.categoryName
): StoreListCategory


data class ModelUiOrderInfoItem(val itemAddedList: List<ModelUIStoreProductItem>) {

    val totalQuantity: Int
        get() {
            var totalCount = 0
            itemAddedList.map {
                totalCount += it.quantityWeight.currentQuantity
            }
            return totalCount
        }

    val totalPrice: Double
        get() {
            var totalPrice = 0.0
            itemAddedList.map {
                val quantity = it.quantityWeight.currentQuantity
                val price =  quantity*it.pricing.showActualPrice
                totalPrice += price
            }
            return totalPrice
        }

    val fullPrice: String
        get() {
            return String.format(Locale.US, "$CURRENCY_SYMBOL%.2f", totalPrice)
        }
}

data class ModelUiOrderInfoContainer(val storeOrders: Map<String, ModelUiOrderInfoItem> = mutableMapOf())

data class ModelUIStoreInfoTiming(
    val status: String,
    val time: String,
) {
    companion object {
        val initial = ModelUIStoreInfoTiming("", "")
    }
}

data class ModelUIStoreInfoHighlight(
    @DrawableRes val icon: Int,
    val text: String,
    val description: String,
    val isHighlightLabel: Boolean,
    val id: Int,
) {
    companion object {
        val initial = ModelUIStoreInfoHighlight(-1, "", "", false, -1)
    }
}

enum class StoreCategory(val categoryName: String) {
    HEADER_INFO("headerInfo"),
    Notes("notes"),
    Available("categories"),
    Other("actualProductItems"),
    Footer("footer"),
}

sealed interface StoreListCategory {
    val id: String
    val category: StoreCategory
}

data class ModelUIStoreMenuNotes(
    val header: String,
    val description: String,
    override val category: StoreCategory = StoreCategory.Notes,
    override val id: String = category.categoryName
) : StoreListCategory

data class ModelUIStoreMenuFooter(
    val name: String,
    override val category: StoreCategory = StoreCategory.Footer,
    override val id: String = category.categoryName
) : StoreListCategory


enum class StoreCategoryOtherOrientation {
    Horizontal,
    Vertical;
}

sealed interface ModelUIStoreMenuOther : StoreListCategory {
    val name: String
    val productList: List<ModelUIStoreItemType>
    val orientationType: StoreCategoryOtherOrientation
    override val category: StoreCategory
        get() = StoreCategory.Other
}

data class ModelUIStoreMenuCategory(
    override val name: String,
    override val productList: List<ModelUIStoreItemType>,
    override val category: StoreCategory = StoreCategory.Available,
    override val id: String = category.categoryName,
    override val orientationType: StoreCategoryOtherOrientation = StoreCategoryOtherOrientation.Vertical
) : ModelUIStoreMenuOther

data class ModelUIProductCategoryItem(
    override val id: String,
    val name: String,
    val image: ModelUIImage,
    override val productSubType: ModelUIStoreProductItemSubType = ModelUIStoreProductItemSubType.Actual
): ModelUIStoreItemType


data class ModelUIStoreProductContainer(
    override val name: String,
    override val productList: List<ModelUIStoreItemType>,
    override val id: String,
    override val orientationType: StoreCategoryOtherOrientation = StoreCategoryOtherOrientation.Horizontal,
) : ModelUIStoreMenuOther


enum class ModelUIStoreProductItemSubType(val item: String) {
    Actual("Actual"),
    Footer("footer");
}

sealed interface ModelUIStoreItemType {
    val id: String
    val productSubType: ModelUIStoreProductItemSubType
}

data class ModelUIStoreItemTypeFooter(
    override val productSubType: ModelUIStoreProductItemSubType = ModelUIStoreProductItemSubType.Footer,
    override val id: String = productSubType.name
) : ModelUIStoreItemType

sealed interface StoreMenuItemQuantityType

object StoreMenuItemQuantityCountable : StoreMenuItemQuantityType

data class StoreMenuItemQuantityWeighable(val unitDetails: UnitDetails) :
    StoreMenuItemQuantityType {
    data class UnitDetails(val stepSize: Int, val unitType: String)
}

data class ModelUIStoreProductItem(
    override val id: String,
    val categoryId: String,
    val name: String,
    val image: ModelUIImage,
    val pricing: ModelUIProductOtherVerticalItemPrice,
    val quantityWeight: ModelUIProductOtherVerticalItemWeight,
    val isAdded: Boolean = false,
    override val productSubType: ModelUIStoreProductItemSubType = ModelUIStoreProductItemSubType.Actual
) : ModelUIStoreItemType {

    companion object Companion {
        const val GRAM = "Gram"
        const val GRAM_SHORT = "g"
        const val KG = "kg"
        const val ONE_KG = 1000.0

        const val CURRENCY_SYMBOL: String = "₪"
    }

    val totalQuantity: String
        get() {
            val quantity = quantityWeight.currentQuantity
            if (quantityWeight.quantityType == StoreMenuItemQuantityCountable) {
                return quantity.toString()
            }

            val weightItem = quantityWeight.quantityType as StoreMenuItemQuantityWeighable
            if (GRAM != weightItem.unitDetails.unitType) {
                return "${quantity}$KG"
            }

            if (quantity == 1) {
                return "${weightItem.unitDetails.stepSize}$GRAM_SHORT"
            }

            return "${(weightItem.unitDetails.stepSize * quantity) / ONE_KG}$KG"
        }
}


data class ModelUIProductOtherVerticalItemWeight(
    val quantityType: StoreMenuItemQuantityType,

    val weightToPresent: String,
    val currentQuantity: Int = 1
)

data class ModelUIProductOtherVerticalItemPrice(
    val discountPercentage: Int,
    val discountPrice: Double,

    val basePrice: Double,

    val specialLabel: String
) {
    val showActualPrice: Double
        get() {
            if (0 < discountPercentage) {
                return discountPrice
            }

            return basePrice
        }
}

val testModelUIImage = ModelUIImage(
"images/Markets/MarketTags/37720240709111640_original.jpg",
";yLN#sXA_4%0?bWBxuRjf6bIoJj?WDjsfPWUj[WCb2t2obNKWAjsWBofj@x[WCf8s,WXayWBWVWB.7WCe:j=RkjsRjf7RkV@j[bba#oeayWXj[j@%2ayWVjtWBWVWBj@j?IWoen#R+j]oft6fRog"
)

val testModelUIProductOtherVerticalItemNoDiscountPrice = ModelUIProductOtherVerticalItemPrice(0, 4.34, 4.34, "")
val testModelUIProductOtherVerticalItemDiscountPrice = ModelUIProductOtherVerticalItemPrice(10, 4.34, 4.34, "10% discount")


val testModelUIProductOtherVerticalItemWeight = ModelUIProductOtherVerticalItemWeight(StoreMenuItemQuantityWeighable(UnitDetails(500, "Gram")), weightToPresent = "1Kg")
val testModelUIProductOtherVerticalItemWeightMultiple = ModelUIProductOtherVerticalItemWeight(StoreMenuItemQuantityWeighable(UnitDetails(500, "Gram")), weightToPresent = "1Kg", 9)

val testModelUIProductOtherVerticalItemCount = ModelUIProductOtherVerticalItemWeight(StoreMenuItemQuantityCountable, weightToPresent = "")


val testModelUIStoreProductCountableNoDiscountItem = ModelUIStoreProductItem(
    "31",
    "1",
    "Apple",
    testModelUIImage,
    testModelUIProductOtherVerticalItemNoDiscountPrice,
    testModelUIProductOtherVerticalItemCount,
    false
)

val testModelUIStoreProductCountableNoDiscountItemAdded = ModelUIStoreProductItem(
    "32",
    "1",
    "Apple",
    testModelUIImage,
    testModelUIProductOtherVerticalItemNoDiscountPrice,
    testModelUIProductOtherVerticalItemCount,
    true
)

val testModelUIStoreProductCountableDiscountItem = ModelUIStoreProductItem(
    "33",
    "1",
    "Apple",
    testModelUIImage,
    testModelUIProductOtherVerticalItemDiscountPrice,
    testModelUIProductOtherVerticalItemCount,
    false
)

val testModelUIStoreProductCountableDiscountItemAdded = ModelUIStoreProductItem(
    "34",
    "1",
    "Apple",
    testModelUIImage,
    testModelUIProductOtherVerticalItemDiscountPrice,
    testModelUIProductOtherVerticalItemCount,
    true
)

val testModelUIStoreProductWeighAbleNoDiscountItem = ModelUIStoreProductItem(
    "35",
    "1",
    "Apple",
    testModelUIImage,
    testModelUIProductOtherVerticalItemNoDiscountPrice,
    testModelUIProductOtherVerticalItemWeight,
    false
)

val testModelUIStoreProductWeighAbleNoDiscountItemAdded = ModelUIStoreProductItem(
    "36",
    "1",
    "111",
    testModelUIImage,
    testModelUIProductOtherVerticalItemNoDiscountPrice,
    testModelUIProductOtherVerticalItemWeight,
    true
)

val testModelUIStoreProductWeighAbleDiscountItem = ModelUIStoreProductItem(
    "37",
    "1",
    "Apple",
    testModelUIImage,
    testModelUIProductOtherVerticalItemDiscountPrice,
    testModelUIProductOtherVerticalItemWeight,
    false
)

val testModelUIStoreProductWeighAbleDiscountItemAddedMultipleTime = ModelUIStoreProductItem(
    "38",
    "1",
    "Apple",
    testModelUIImage,
    testModelUIProductOtherVerticalItemDiscountPrice,
    testModelUIProductOtherVerticalItemWeightMultiple,
    true
)

val testModelUIStoreProductWeighAbleDiscountItemAdded = ModelUIStoreProductItem(
    "39",
    "1",
    "Apple",
    testModelUIImage,
    testModelUIProductOtherVerticalItemDiscountPrice,
    testModelUIProductOtherVerticalItemWeight,
    true
)

val testModelUIStoreProductContainer = ModelUIStoreProductContainer("Deal Horizontal", id ="Deal1Horizontal", productList = listOf(
    testModelUIStoreProductCountableNoDiscountItem,
    testModelUIStoreProductCountableNoDiscountItemAdded,
    testModelUIStoreProductCountableDiscountItem,
    testModelUIStoreProductCountableDiscountItemAdded,
    testModelUIStoreProductWeighAbleNoDiscountItem,
    testModelUIStoreProductWeighAbleNoDiscountItemAdded,
    testModelUIStoreProductWeighAbleDiscountItem,
    testModelUIStoreProductWeighAbleDiscountItemAddedMultipleTime,
    testModelUIStoreProductWeighAbleDiscountItemAdded,
    ModelUIStoreItemTypeFooter()
))

val testModelUIStoreMenuOtherVertical = ModelUIStoreProductContainer("Deal Other Vertical", id ="Deal1Vertical", productList = listOf(
    testModelUIStoreProductCountableNoDiscountItem,
    testModelUIStoreProductCountableNoDiscountItemAdded,
    testModelUIStoreProductCountableDiscountItem,
    testModelUIStoreProductCountableDiscountItemAdded,
    testModelUIStoreProductWeighAbleNoDiscountItem,
    testModelUIStoreProductWeighAbleNoDiscountItemAdded,
    testModelUIStoreProductWeighAbleDiscountItem,
    testModelUIStoreProductWeighAbleDiscountItemAddedMultipleTime,
    testModelUIStoreProductWeighAbleDiscountItemAdded
))

val testModelUIStoreMenuCategoryItem = ModelUIProductCategoryItem("1", "Fruits", ModelUIImage("images/TagInventory/30_20250612080729_original.jpg", ""))

val testModelUIStoreMenuCategory = ModelUIStoreMenuCategory("Available Categories", id = "idAvailableCategory", productList = listOf(
    ModelUIProductCategoryItem("2", "Vegetable", ModelUIImage("images/Markets/MarketTags/38720240709111418_original.jpg", "")),
    ModelUIProductCategoryItem("3", "Health", ModelUIImage("images/Markets/MarketTags/37720240709111640_original.jpg", ""))
))

val testModelUIStoreMenuFooter = ModelUIStoreMenuFooter("Please connect with customer care")

val testModelUiOrderInfo = ModelUiOrderInfoContainer(mutableMapOf("1" to ModelUiOrderInfoItem(listOf(testModelUIStoreProductCountableNoDiscountItem))))

val testModelUIStoreMenuNotes = ModelUIStoreMenuNotes("Notes",
    "It is a long established fact that a reader will be distracted by the readable content of a page when looking at its layout. The point of using Lorem Ipsum is that it has a more-or-less normal distribution of letters, as opposed to using 'Content here, content here', making it look like readable English. Many desktop publishing packages and web page editors now use Lorem Ipsum as their default model text, and a search for 'lorem ipsum' will uncover many web sites still in their infancy. Various versions have evolved over the years, sometimes by accident, " +
            "sometimes on purpose (injected humour and the like).", id = "notesId")

val testModelUIHeaderInfo = ModelUIHeaderInfo(ModelUIImage("https://images.pexels.com/photos/70497/pexels-photo-70497.jpeg?_gl=1*1birq7u*_ga*MTMxNzYyMTYzNS4xNzYzMTg5MzU5*_ga_8JE65Q40S6*czE3NjMxODkzNTkkbzEkZzAkdDE3NjMxODkzNTkkajYwJGwwJGgw", ""),
    ModelUIImage("images/TagInventory/30_20250612080729_original.jpg", ""),
    testModelUIStoreMenuCategory.productList as List<ModelUIProductCategoryItem>
)

val testModelUIStoreInfo = ModelUIStoreInfo(
    icon = ModelUIImage("images/TagInventory/30_20250612080729_original.jpg", ""),
    name = "Carefourn supermarket", address ="Dubail, shafia, israle",
    time = ModelUIStoreInfoTiming("Open", "Til 11 AM"),
    highLightList = listOf(
        ModelUIStoreInfoHighlight(icon = R.drawable.icon_delivery, "", "Delivery", false,1),
        ModelUIStoreInfoHighlight(icon = R.drawable.icon_schedule,  "Sun", "8-4",false,2),
        ModelUIStoreInfoHighlight(icon = R.drawable.icon_rating,  "4.2(5++)", "Top Rated",true,3),
    )
)

val testStoreListCategory = listOf(
    testModelUIStoreInfo,
    testModelUIStoreMenuNotes,
    testModelUIStoreMenuCategory,
    testModelUIStoreMenuOtherVertical,
    testModelUIStoreProductContainer,
    testModelUIStoreMenuFooter

)
val testStoreModelUIStoreContainer= ModelUIStoreContainer(
    testModelUIHeaderInfo, testStoreListCategory, testModelUiOrderInfo
)