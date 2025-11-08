package dev.reprator.haat.features.restaurant2pane.businessDetail.data.remote.mapper

import dev.reprator.haat.R
import dev.reprator.haat.features.restaurant2pane.businessDetail.data.remote.modal.EntityStoreInfoContainer
import dev.reprator.haat.features.restaurant2pane.businessDetail.data.remote.modal.EntityStoreMenuContainer
import dev.reprator.haat.features.restaurant2pane.businessDetail.data.remote.modal.EntityStoreProductItem
import dev.reprator.haat.features.restaurant2pane.businessDetail.data.remote.modal.EntityStoreSectionContainer
import dev.reprator.haat.features.restaurant2pane.businessDetail.domain.modal.ModelUIHeaderInfo
import dev.reprator.haat.features.restaurant2pane.businessDetail.domain.modal.ModelUIProductCategoryItem
import dev.reprator.haat.features.restaurant2pane.businessDetail.domain.modal.ModelUIProductOtherVerticalItemPrice
import dev.reprator.haat.features.restaurant2pane.businessDetail.domain.modal.ModelUIProductOtherVerticalItemWeight
import dev.reprator.haat.features.restaurant2pane.businessDetail.domain.modal.ModelUIStoreContainer
import dev.reprator.haat.features.restaurant2pane.businessDetail.domain.modal.ModelUIStoreInfo
import dev.reprator.haat.features.restaurant2pane.businessDetail.domain.modal.ModelUIStoreInfoHighlight
import dev.reprator.haat.features.restaurant2pane.businessDetail.domain.modal.ModelUIStoreInfoTiming
import dev.reprator.haat.features.restaurant2pane.businessDetail.domain.modal.ModelUIStoreItemType
import dev.reprator.haat.features.restaurant2pane.businessDetail.domain.modal.ModelUIStoreItemTypeFooter
import dev.reprator.haat.features.restaurant2pane.businessDetail.domain.modal.ModelUIStoreMenuCategory
import dev.reprator.haat.features.restaurant2pane.businessDetail.domain.modal.ModelUIStoreMenuFooter
import dev.reprator.haat.features.restaurant2pane.businessDetail.domain.modal.ModelUIStoreMenuNotes
import dev.reprator.haat.features.restaurant2pane.businessDetail.domain.modal.ModelUIStoreProductContainer
import dev.reprator.haat.features.restaurant2pane.businessDetail.domain.modal.ModelUIStoreProductItem
import dev.reprator.haat.features.restaurant2pane.businessDetail.domain.modal.ModelUIStoreProductItemSubType
import dev.reprator.haat.features.restaurant2pane.businessDetail.domain.modal.StoreCategory
import dev.reprator.haat.features.restaurant2pane.businessDetail.domain.modal.StoreCategoryOtherOrientation
import dev.reprator.haat.features.restaurant2pane.businessDetail.domain.modal.StoreListCategory
import dev.reprator.haat.features.restaurant2pane.businessDetail.domain.modal.StoreMenuItemQuantityCountable
import dev.reprator.haat.features.restaurant2pane.businessDetail.domain.modal.StoreMenuItemQuantityType
import dev.reprator.haat.features.restaurant2pane.businessDetail.domain.modal.StoreMenuItemQuantityWeighable
import dev.reprator.haat.features.restaurant2pane.restaurants.data.remote.mapper.mapToImage
import dev.reprator.haat.features.restaurant2pane.restaurants.domain.modal.ModelUIImage
import dev.reprator.haat.features.restaurant2pane.restaurants.domain.modal.StoreOperatingTimeStatus.Companion.convertToStoreOperatingTimeStatus
import dev.reprator.haat.util.Mapper
import javax.inject.Inject

const val MIN_HORIZONTAL_ITEM_TO_SHOW_VIEW_ALL = 4

class StoreMenuMapper @Inject constructor() : Mapper<Pair<EntityStoreMenuContainer, EntityStoreInfoContainer>?, ModelUIStoreContainer> {

    override suspend fun map(from: Pair<EntityStoreMenuContainer, EntityStoreInfoContainer>?): ModelUIStoreContainer {
        if (null == from) {
            throw Exception("Invalid data")
        }
        val (inputMenuContainer, inputMenuInfo) = from

        var headerInfo = inputMenuInfo.mapInfoToModelUIHeaderInfo()

        val menuList = mutableListOf<StoreListCategory>()

        val headerListItem = inputMenuInfo.mapInfoToModelUIStoreInfo()
        menuList.add(headerListItem)

        val notes = inputMenuInfo.mapInfoToModelUIStoreMenuNotes()
        if(null != notes)
            menuList.add(notes)

        inputMenuContainer.sections?.filter {
            null != it.type
        }?.forEachIndexed { index, it ->
            when(it.type) {

                "Categories" -> {
                    val (menuCategory, categoryList) = it.mapCategoriesListToModelUIStoreMenuCategory(index)
                    menuList.add(menuCategory)

                    headerInfo = headerInfo.copy(categoryList = categoryList as List<ModelUIProductCategoryItem>)
                }

                "Footer" -> {
                    val footer = it.mapFooterToModelUIStoreMenuFooter()
                    menuList.add(footer)
                }

                else -> {
                    val product = it.mapProductsToModelUIStoreProductContainer(index)
                    menuList.add(product)
                }
            }
        }

        return ModelUIStoreContainer(headerInfo, menuList)
    }

    private fun EntityStoreInfoContainer.mapInfoToModelUIHeaderInfo(): ModelUIHeaderInfo {
        val bannerModelUIImage = bannerImages?.firstOrNull()?.mapToImage() ?: ModelUIImage.initial
        val iconModelUIImage = iconImage.mapToImage()
        return ModelUIHeaderInfo(bannerModelUIImage, iconModelUIImage, emptyList())
    }

    private fun EntityStoreInfoContainer.mapInfoToModelUIStoreInfo(): ModelUIStoreInfo {
        val iconModelUIImage = iconImage.mapToImage()
        val name = name?.enUS.orEmpty()
        val address = location?.address.orEmpty()

        val storeInfoTiming = ModelUIStoreInfoTiming(status?.status?.convertToStoreOperatingTimeStatus()!!.stringId ,"Till 11 AM")

        val highLightList = listOf(
            ModelUIStoreInfoHighlight(icon = R.drawable.icon_delivery, "", "Delivery", false,1),
            ModelUIStoreInfoHighlight(icon = R.drawable.icon_schedule,  "Sun", "8-4",false,2),
            ModelUIStoreInfoHighlight(icon = R.drawable.icon_rating,  "${rating?.value.orEmpty()}${rating?.numberOfRatings.orEmpty()}", "Top Rated",rating?.topRated?: true,3),
        )

        return ModelUIStoreInfo(name, address, iconModelUIImage, storeInfoTiming, highLightList)
    }

    private fun EntityStoreInfoContainer.mapInfoToModelUIStoreMenuNotes(): ModelUIStoreMenuNotes? {
        val noteItem = notes?.firstOrNull()?: return null
        return ModelUIStoreMenuNotes(noteItem.type.orEmpty(), noteItem.text.orEmpty())
    }

    private fun EntityStoreSectionContainer.mapProductsToModelUIStoreProductContainer(rootIndex: Int): ModelUIStoreProductContainer {
        val categoryId = "$id$rootIndex${StoreCategory.Other.categoryName}"
        val productList = items?.mapIndexed { index, item ->

            val discountPercentage = item.discountPercentage ?: 0
            val pricing = ModelUIProductOtherVerticalItemPrice(discountPercentage,
                item.discountPrice ?: 0.0, item.basePrice ?: 0.0,
                if(0 < discountPercentage) "$discountPercentage% discount available" else "")

            val quantityWeight: ModelUIProductOtherVerticalItemWeight = item.mapTypeToStoreMenuItemQuantityType()

            ModelUIStoreProductItem(
                id = "${item.productCode}${item.id ?:0}$rootIndex${item.categoryId}${item.subCategoryId}${index}",
                categoryId = categoryId,
                name = item.name?.enUS.orEmpty(),
                image = item.productImages?.firstOrNull()?.mapToImage() ?: ModelUIImage.initial,
                quantityWeight = quantityWeight, pricing = pricing,
            )
        }.orEmpty()

        val orientation = orientation.mapLayoutToStoreCategoryOtherOrientation()
        val updatedProductList = if((orientation == StoreCategoryOtherOrientation.Horizontal) && (MIN_HORIZONTAL_ITEM_TO_SHOW_VIEW_ALL < productList.size)) {
            val itemFooter = ModelUIStoreItemTypeFooter(id = "$categoryId${ModelUIStoreProductItemSubType.Footer.item}")
            val newProductList: MutableList<ModelUIStoreItemType> = productList.toMutableList()
            newProductList.add(itemFooter)
            newProductList
        } else {
            productList
        }
        return ModelUIStoreProductContainer(name = name.orEmpty(),
            id = categoryId,
            orientationType = orientation,
            productList = updatedProductList)
    }

    private fun EntityStoreProductItem?.mapTypeToStoreMenuItemQuantityType(): ModelUIProductOtherVerticalItemWeight {
        val itemQuantityType: StoreMenuItemQuantityType =  if("Weighable" == this?.quantityType) {
            val unitDetails = StoreMenuItemQuantityWeighable.UnitDetails(unitDetails?.stepSize?.toInt() ?: 1,
                this.unitDetails?.stepSize.orEmpty())
            StoreMenuItemQuantityWeighable(unitDetails)
        } else {
            StoreMenuItemQuantityCountable
        }

       return ModelUIProductOtherVerticalItemWeight(quantityType = itemQuantityType, weightToPresent = this?.weightToPresent.orEmpty())
    }

    private fun EntityStoreSectionContainer.mapCategoriesListToModelUIStoreMenuCategory(rootIndex: Int): Pair<ModelUIStoreMenuCategory, List<ModelUIStoreItemType>> {

        val categoryList = categories?.mapIndexed { index, item ->
            ModelUIProductCategoryItem("$index$rootIndex${item.id}", item.name.orEmpty(),
                item.image.mapToImage())
        }.orEmpty()

        val orientation = orientation.mapLayoutToStoreCategoryOtherOrientation()
        val updatedProductList = if((orientation == StoreCategoryOtherOrientation.Horizontal) && (MIN_HORIZONTAL_ITEM_TO_SHOW_VIEW_ALL < categoryList.size)) {
            val itemFooter = ModelUIStoreItemTypeFooter(id = "$rootIndex${ModelUIStoreProductItemSubType.Footer.item}")
            val newCategoryList: MutableList<ModelUIStoreItemType> = categoryList.toMutableList()
            newCategoryList.add(itemFooter)
            newCategoryList
        } else {
            categoryList
        }

        return ModelUIStoreMenuCategory(name = name.orEmpty(),
            orientationType = orientation,
            productList = updatedProductList) to categoryList
    }

    private fun String?.mapLayoutToStoreCategoryOtherOrientation(): StoreCategoryOtherOrientation {
        return if("Vertical" == this) {
            StoreCategoryOtherOrientation.Vertical
        } else {
            StoreCategoryOtherOrientation.Horizontal
        }
    }

    private fun EntityStoreSectionContainer.mapFooterToModelUIStoreMenuFooter(): ModelUIStoreMenuFooter {
        return ModelUIStoreMenuFooter(name.orEmpty())
    }
}
