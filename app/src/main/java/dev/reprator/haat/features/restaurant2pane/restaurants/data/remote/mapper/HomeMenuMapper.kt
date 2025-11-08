package dev.reprator.haat.features.restaurant2pane.restaurants.data.remote.mapper

import dev.reprator.haat.features.restaurant2pane.restaurants.data.remote.modal.EntityMenuCategory
import dev.reprator.haat.features.restaurant2pane.restaurants.data.remote.modal.EntityMenuContainer
import dev.reprator.haat.features.restaurant2pane.restaurants.data.remote.modal.EntityMenuImages
import dev.reprator.haat.features.restaurant2pane.restaurants.data.remote.modal.EntityMenuRating
import dev.reprator.haat.features.restaurant2pane.restaurants.domain.modal.CategoriesMarketHorizontal
import dev.reprator.haat.features.restaurant2pane.restaurants.domain.modal.HomeCategory
import dev.reprator.haat.features.restaurant2pane.restaurants.domain.modal.HomeCategory.Companion.convertToHomeCategory
import dev.reprator.haat.features.restaurant2pane.restaurants.domain.modal.MainCategories
import dev.reprator.haat.features.restaurant2pane.restaurants.domain.modal.MarketHorizontaSubCategoryHighlight
import dev.reprator.haat.features.restaurant2pane.restaurants.domain.modal.MarketHorizontalSubCategoryNormal
import dev.reprator.haat.features.restaurant2pane.restaurants.domain.modal.MarketHorizontalSubCategorySponsered
import dev.reprator.haat.features.restaurant2pane.restaurants.domain.modal.ModelUIBanner
import dev.reprator.haat.features.restaurant2pane.restaurants.domain.modal.ModelUIBannerContainer
import dev.reprator.haat.features.restaurant2pane.restaurants.domain.modal.ModelUIImage
import dev.reprator.haat.features.restaurant2pane.restaurants.domain.modal.ModelUIMarketContainer
import dev.reprator.haat.features.restaurant2pane.restaurants.domain.modal.ModelUIMarketHighlightContainer
import dev.reprator.haat.features.restaurant2pane.restaurants.domain.modal.ModelUIMarketSponsoredContainer
import dev.reprator.haat.features.restaurant2pane.restaurants.domain.modal.ModelUIMarketStore
import dev.reprator.haat.features.restaurant2pane.restaurants.domain.modal.ModelUIMarketVerticalContainer
import dev.reprator.haat.features.restaurant2pane.restaurants.domain.modal.ModelUIMenuContainer
import dev.reprator.haat.features.restaurant2pane.restaurants.domain.modal.ModelUIMenuUserInfo
import dev.reprator.haat.features.restaurant2pane.restaurants.domain.modal.ModelUIPromotedBannerContainer
import dev.reprator.haat.features.restaurant2pane.restaurants.domain.modal.ModelUIRating
import dev.reprator.haat.features.restaurant2pane.restaurants.domain.modal.ModelUISuggestedMarket
import dev.reprator.haat.features.restaurant2pane.restaurants.domain.modal.ModelUISuggestedMarketContainer
import dev.reprator.haat.features.restaurant2pane.restaurants.domain.modal.ModelUITag
import dev.reprator.haat.features.restaurant2pane.restaurants.domain.modal.ModelUITagContainer
import dev.reprator.haat.features.restaurant2pane.restaurants.domain.modal.StoreOperatingTimeStatus.Companion.convertToStoreOperatingTimeStatus
import dev.reprator.haat.util.Mapper
import javax.inject.Inject

class HomeMenuMapper @Inject constructor() : Mapper<EntityMenuContainer?, ModelUIMenuContainer> {

    override suspend fun map(from: EntityMenuContainer?): ModelUIMenuContainer {

        if (null == from) {
            throw Exception("Invalid data")
        }

        val userInfo = ModelUIMenuUserInfo(
            from.userAddresses?.addressHintMessage.orEmpty(),
            from.userAddresses?.showDisruptionMessage ?: false,
            from.userAddresses?.addresses?.firstOrNull()?.city.orEmpty())

        val categories = mutableListOf<MainCategories>()

        val bannerList = from.mainPageBanners?.banners?.mapIndexed { index, banner ->
            ModelUIBanner(banner.businessId ?: index.toString(), banner.businessName?.enUS.orEmpty(), banner.image.mapToImage())
        }.orEmpty()
        categories.add(ModelUIBannerContainer(from.mainPageBanners?.interval ?: 2, bannerList))


        val tagList = from.tags?.tags?.mapIndexed { index, item ->
            ModelUITag(item.id ?: index.toString(), item.name.orEmpty(), item.images.mapToImage())
        }.orEmpty()
        categories.add(ModelUITagContainer(from.tags?.title.orEmpty(), tagList))

        from.categories?.mapIndexed { index, item ->

            val categoryUiElement = item.mapToCategory(index)
            if(null != categoryUiElement)
                categories.add(categoryUiElement)
        }

        return ModelUIMenuContainer(userInfo, categories)
    }

}

private fun EntityMenuCategory?.mapToCategory(rootIndex: Int): MainCategories? {
    val elementType = this?.elementType?.convertToHomeCategory()
    val categoryUiElement = when(elementType) {

        HomeCategory.SuggestedMarketsCategory -> {
            val storeList = this.stores?.mapIndexed { index, item ->
                val foreGroundList = item.foregroundImage?.mapIndexed {  index, item ->
                    item.mapToImage()
                }.orEmpty()
                val backgroundImage = item.backgroundImage.mapToImage()

                ModelUISuggestedMarket(item.storeId ?: index.toString(),
                    item.title.orEmpty(), item.subTitle.orEmpty(),
                    backgroundImage, foreGroundList)
            }.orEmpty()
            ModelUISuggestedMarketContainer(this.name.orEmpty(), storeList,
                id = "${this.id.orEmpty()}${this.priority?.or(1)}${elementType.categoryName}$rootIndex")
        }

        HomeCategory.GeneralPromotedBanner,
        HomeCategory.MarketPromotedBanner -> {
            ModelUIPromotedBannerContainer(this.businessId ?: this.entityId ?: this.id ?: "", this.image.mapToImage(),
                category= elementType, id = "${this.id.orEmpty()}${this.priority?.or(1)}${elementType.categoryName}$rootIndex")
        }

        HomeCategory.MarketHorizontalCategory -> {
            this.mapToMarketHorizontalCategory(rootIndex)
        }
        HomeCategory.MarketVerticalCategory -> {
            this.mapToMarketVerticalCategory(rootIndex)
        } else -> {
            null
        }
    }
    return categoryUiElement
}

fun EntityMenuCategory.mapToMarketVerticalCategory(rootIndex: Int): ModelUIMarketVerticalContainer {

    val storeList = this.stores?.mapIndexed { index, store ->
        val isNew = store.isNew ?: false
        ModelUIMarketStore(store.storeId ?: index.toString(), store.name.orEmpty(),
            store.address.orEmpty(), if(isNew) null else store.rating.mapToRating(),
            isNew, store.businessCardImage.mapToImage(), store.status?.convertToStoreOperatingTimeStatus()!!, emptyList())
    }.orEmpty()

    return ModelUIMarketVerticalContainer(storeList, this.name.orEmpty(),
        "${this.id.orEmpty()}${this.priority?.or(1)}${HomeCategory.MarketVerticalCategory.categoryName}$rootIndex")
}

fun EntityMenuCategory.mapToMarketHorizontalCategory(rootIndex: Int): CategoriesMarketHorizontal {
    val categoryName = HomeCategory.MarketHorizontalCategory.categoryName

    val storeList = this.stores?.mapIndexed { index, store ->
        val isNew = store.isNew ?: false
        ModelUIMarketStore(store.storeId ?: index.toString(), store.name.orEmpty(),
            store.address.orEmpty(), if(isNew) null else store.rating.mapToRating(),
            isNew, store.businessCardImage.mapToImage(), store.status?.convertToStoreOperatingTimeStatus()!!, emptyList())
    }.orEmpty()

    val horizontalCategory = when {
        ((null != this.topImage?.blurhashImage) || (null != this.topImage?.serverImage)) &&  ((null != this.backgroundImage?.serverImage) || (null != this.backgroundImage?.blurhashImage)) -> {
            ModelUIMarketHighlightContainer(
                this.image.mapToImage(),
                this.backgroundColor ?: "#FFFFFF",
                storeList,
                "${this.id.orEmpty()}${this.priority?.or(1)}${MarketHorizontaSubCategoryHighlight}$categoryName$rootIndex",
                name= this.name.orEmpty()
            )
        }

        (true == this.isSponsored) -> {
            ModelUIMarketSponsoredContainer(
                storeList,
                "${this.id.orEmpty()}${this.priority?.or(1)}${MarketHorizontalSubCategorySponsered}$categoryName$rootIndex",
                name= this.name.orEmpty())
        }

        else -> {
            ModelUIMarketContainer(storeList,
                "${this.id.orEmpty()}${this.priority?.or(1)}${MarketHorizontalSubCategoryNormal}$categoryName$rootIndex",
                name= this.name.orEmpty())
        }
    }

    return horizontalCategory
}

private fun EntityMenuRating?.mapToRating(): ModelUIRating {
    return ModelUIRating(this?.value.orEmpty(), this?.numberOfRatings.orEmpty())
}

fun EntityMenuImages?.mapToImage(): ModelUIImage {
    return ModelUIImage(this?.serverImage.orEmpty(), this?.blurhashImage.orEmpty())
}