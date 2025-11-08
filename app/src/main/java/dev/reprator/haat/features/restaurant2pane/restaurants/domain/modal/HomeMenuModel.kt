package dev.reprator.haat.features.restaurant2pane.restaurants.domain.modal

enum class HomeCategory(val categoryName: String) {
    MainPageBanners("mainPageBanners"),
    Tags("tags"),
    SuggestedMarketsCategory("SuggestedMarketsCategory"),

    GeneralPromotedBanner("GeneralPromotedBanner"),
    MarketPromotedBanner("MarketPromotedBanner"),


    MarketHorizontalCategory("MarketHorizontalCategory"),
    MarketVerticalCategory("MarketVerticalCategory");

    companion object {
        fun String.convertToHomeCategory(): HomeCategory? = entries.firstOrNull { it.categoryName == this }
    }
}



data class ModelUIMenuContainer(val userInfo: ModelUIMenuUserInfo, val categories: List<MainCategories>) {
    companion object {
        val initial = ModelUIMenuContainer(ModelUIMenuUserInfo.initial, emptyList())
    }
}

data class ModelUIMenuUserInfo(val addressHintMessage: String,
                    val showDisruptionMessage: Boolean, val address: String) {
    companion object {
        val initial = ModelUIMenuUserInfo("", false, "")
    }
}


sealed interface MainCategories {
    val id: String
    val category: HomeCategory
}

data class ModelUIBannerContainer(
    val interval: Int, val imageBannerList: List<ModelUIBanner>,
    override val category: HomeCategory = HomeCategory.MainPageBanners,
    override val id: String = category.categoryName
) : MainCategories

data class ModelUIBanner(val id: String, val name: String, val image: ModelUIImage)


data class ModelUITagContainer(
    val name: String, val imageList: List<ModelUITag>,
    override val category: HomeCategory = HomeCategory.Tags,
    override val id: String = category.categoryName
) : MainCategories

data class ModelUITag(val id: String, val name: String, val image: ModelUIImage)


data class ModelUISuggestedMarketContainer(
    val name: String,
    val suggestedMarketList: List<ModelUISuggestedMarket>,
    override val category: HomeCategory = HomeCategory.SuggestedMarketsCategory,
    override val id: String
) : MainCategories

data class ModelUISuggestedMarket(
    val id: String,
    val title: String,
    val subTitle: String,
    val background: ModelUIImage,
    val foreground: List<ModelUIImage>
)


sealed interface CategoriesPromotedBanner : MainCategories
data class ModelUIPromotedBannerContainer(
    val businessId: String,
    val image: ModelUIImage,
    override val category: HomeCategory,
    override val id: String
) : CategoriesPromotedBanner


const val MarketHorizontalSubCategoryNormal = "normal"
const val MarketHorizontalSubCategorySponsered = "sponsered"
const val MarketHorizontaSubCategoryHighlight = "highlight"

sealed interface CategoriesMarketHorizontal : MainCategories {
    override val category: HomeCategory
        get() = HomeCategory.MarketHorizontalCategory
    val name: String
    val subType: String
    val stores: List<ModelUIMarketStore>
}

data class ModelUIMarketContainer(
    override val stores: List<ModelUIMarketStore>,
    override val id: String, override val name: String,
    override val subType: String = MarketHorizontalSubCategoryNormal
) : CategoriesMarketHorizontal

data class ModelUIMarketSponsoredContainer(
    override val stores: List<ModelUIMarketStore>,
    override val id: String,
    override val name: String,
    override val subType: String = MarketHorizontalSubCategorySponsered,
) : CategoriesMarketHorizontal

data class ModelUIMarketHighlightContainer(
    val image: ModelUIImage,
    val backgroundColor: String,
    override val stores: List<ModelUIMarketStore>,
    override val id: String,
    override val name: String,
    override val subType: String = MarketHorizontaSubCategoryHighlight,
) : CategoriesMarketHorizontal

data class ModelUIMarketStore(
    val id: String, val name: String, val address: String,
    val rating: ModelUIRating?,
    val isNew: Boolean,
    val image: ModelUIImage,
    val status: StoreOperatingTimeStatus,
    val labels: List<String>
)

data class ModelUIRating(val value: String, val numberOfRatings: String)

data class ModelUIMarketVerticalContainer(
    private val originalStores: List<ModelUIMarketStore>,
    val name: String,
    override val id: String,
    override val category: HomeCategory = HomeCategory.MarketVerticalCategory,
) : MainCategories {

    private val maxItem: Int = 8
    val isViewAll: Boolean = originalStores.size > maxItem

    val stores: List<ModelUIMarketStore>
        get() {
            if (!isViewAll) {
                return originalStores
            }

            return originalStores.take(maxItem)
        }
}

val testBannerContainer = ModelUIBannerContainer(
    6, listOf(
        ModelUIBanner(
            "1",
            "Breakfast",
            image = ModelUIImage(
                "images/TagInventory/30_20250612080729_original.jpg",
                ";OOgKNj[D%j[4nj[00f600aefkj[j[j[fQj[ayay4nj[ayj[oLayt7ayt7t7j[ayj[aya|ayayay4Tfkofj[offQt7ayt7t7fkaej[WBfQayayfQ4nfQofj[ofayt7fQt7offQayj[ayfQfQfQay"
            )
        ),
        ModelUIBanner(
            "2",
            "Lunch",
            image = ModelUIImage(
                "images/Markets/MarketTags/38720240709111418_original.jpg",
                ";GMp5=o1RYt4tSjbtlV_tkw]j?W;f6WEbHjsazoJ9Saz?sWEyCodtloxo#nkWEWEj@odf7o0ocWX_IkAOHj@o~WEo}WEtQsmodoxWERnf6jtafj[SRWEyBj@tRoxtRjtozs.V_RnkAoeocfkocj@"
            )
        ),
        ModelUIBanner(
            "3",
            "Dinner",
            image = ModelUIImage(
                "images/Markets/MarketTags/37720240709111640_original.jpg",
                ";yLN#sXA_4%0?bWBxuRjf6bIoJj?WDjsfPWUj[WCb2t2obNKWAjsWBofj@x[WCf8s,WXayWBWVWB.7WCe:j=RkjsRjf7RkV@j[bba#oeayWXj[j@%2ayWVjtWBWVWBj@j?IWoen#R+j]oft6fRog"
            )
        ),
    )
)

val testTagContainer = ModelUITagContainer(
    "What Are You Looking For?🤔", listOf(
        ModelUITag(
            "506",
            "MEAT & FISHa",
            image = ModelUIImage(
                "images/TagInventory/30_20250612080729_original.jpg",
                ";xOV}ss.?^SLx^n+xba#oMxaa#R*j?afoLoKWVay?^WVaLs.MxbHRPj[R*R-j@o0jZj[WpWXjtj[x]bGM_n+WAWoogjst7jZj?a{WWoeoLf8oeayxboKNFWWogoJt7azWCoJWVWXoeoLayayazf6"
            )
        ),
        ModelUITag(
            "387",
            "MEAT & FISH",
            image = ModelUIImage(
                "images/Markets/MarketTags/38720240709111418_original.jpg",
                ";xOffLs.?^SLx^n+xba#oMxaa#R*j?afoLoKWVay?^WVaLs.MxbHRPj@R*R,j@o0jZj[WpWXjtj[x]bGM_n+WAWoogjst7jZj?a{WWoeoLf8oeayxboKNFWVogoJt7azWCocWVWXoeoLayayazf6"
            )
        ),
        ModelUITag(
            "377",
            "ROASTERY",
            image = ModelUIImage(
                "images/Markets/MarketTags/37720240709111640_original.jpg",
                ";wOC?NWV_NnjtmoexuW=oft7j[jYayS2f7jZj[fQ.To2oef+MxazRijZV@WXafWBkBoKj[j[ayj[%gj@MxWWaKayX8f6t7s.fkR+j@ofWCbHjtfPxbbHRPjZkCayt7j[j[ayjZj[ayj[j[aya|WC"
            )
        ),
    )
)

val testSuggestedMarketContainer = ModelUISuggestedMarketContainer(
    "Suggested Stores", listOf(
        ModelUISuggestedMarket(
            "506",
            background = ModelUIImage(
                "images/StoreInFeaturedBanner/storeInFeaturedBanner_background_VG4UIHYT20240703135116_auto_quality.jpg",
                ";nPi@sof?wWWxuj[nhfPf5%Mj[Rjayayayaxj[az.TWCi^oeM{aybcj[bIWXazWBj[j?j[ofaxkB%LoeM{aebvkCaef6aeWVfQaejtozayj[fkaeniayW=bHayjZjFj[ofWAfPofj[ofayWBf6ay"
            ),
            title = "df",
            subTitle = "asdf",
            foreground = listOf(
                ModelUIImage(
                    "images/StoreInFeaturedBanner/storeInFeaturedBanner_JPKFP6LQ20240703135118_auto_quality.jpg",
                    ";yM@4Pt6_NWC%MWBt8WXt7xaayWVfkayoeayR*j?_4WBazbGM{j]Ris.V@bIj[WBazoJaya}odofx]a}RPoKRjoLbIWBogj]j[WBayofayoekBWCt8a}RPoKkCj[t7WCj[WWWVoKoebIWCjsoLWC"
                ),
                ModelUIImage(
                    "images/StoreInFeaturedBanner/storeInFeaturedBanner_2YR1CTAT20240703135119_auto_quality.jpg",
                    ";yMjmxj[_4az-;WCo#s:xtt7j[WBjsf6fPWCayfP?wj[s,j@IUa|Ria|M|kCfPWBWVa#j[ofoej[-;WCInj@Rjs.f+WCt7oef6WVj[kCa}bHayf6t8oeV?WCWYWVxtfPj[R*ayoej[ofj@ayWVWB"
                ),
                ModelUIImage(
                    "images/StoreInFeaturedBanner/storeInFeaturedBanner_XTG80PWG20240703135121_auto_quality.jpg",
                    ";tO3CBay?^az%Mj@tRbHxuofayjaaya{j[ayj[jZ.Ta|xGoeM{a#RPjtRjj]ayWBoff8afj[ayj[%MjuNGWVRPj?WBf6oft6j[WCa{oeoJj[WBa|xufkM{jZaea#tRfkofjFbHj@aebIW;j[oJae"
                ),
                ModelUIImage(
                    "images/StoreInFeaturedBanner/storeInFeaturedBanner_HUZVEWQI20240703135122_auto_quality.jpg",
                    ";yMjmxj[_4az-;WCo#s:xtt7j[WBjsf6fPWCayfP?wj[s,j@IUa|Ria|M|kCfPWBWVa#j[ofoej[-;WCInj@Rjs.f+WCt7oef6WVj[kCa}bHayf6t8oeV?WCWYWVxtfPj[R*ayoej[ofj@ayWVWB"
                ),
            )
        ),

        ModelUISuggestedMarket(
            "508",
            background = ModelUIImage(
                "images/StoreInFeaturedBanner/storeInFeaturedBanner_background_2025_06_16_10_45_15_734_auto_quality.jpg",
                ";aS6GNwcxGWBkCR*t7j[t7ofa|ayj[f6fkayfQjt_NShR*ofaes:Rjj[V@WBjsj[WVfkf6j[j[az8_s9s:WBkCR*ofa|oft7azayoLayfkayayj[.8W=WCofaeofWBayWBWBoKj[WVj[f6oLj[WV"
            ),
            title = "Test",
            subTitle = "Test Subtitle",
            foreground = listOf(
                ModelUIImage(
                    "images/StoreInFeaturedBanner/storeInFeaturedBanner_2025_06_16_10_45_15_887_auto_quality.jpg",
                    ";JL4g2.7IAa~x[IBtjIBx[DNX3Vtoff+r?N_niR+.8aeoxj@ogadWYoLWF9sV@ozkDaeX7xERkja%es*xuozoLWqjGbbjZRNxsoIkBogWVkCWXj[?Hj]WAaybIodj@ofayWFkDWBs,kBWBoekBoJ"
                ),
                ModelUIImage(
                    "images/StoreInFeaturedBanner/storeInFeaturedBanner_2025_06_16_10_45_15_932_auto_quality.jpg",
                    ";vRUscofrAWBVro0WBoeogs:ayWBoLj[fQfQWVazq?WBS%ofo~bGogWWWBR*oLs:WBWCj[fRoej@ROj[tmWVV?j[V@j?oKs.ayWCoeoLWVf6azazkXayadoLadf6kCaybbj[fjf7ayfPoefRayj?"
                ),
                ModelUIImage(
                    "images/StoreInFeaturedBanner/storeInFeaturedBanner_2025_06_16_10_45_15_973_auto_quality.jpg",
                    ";SS3[}ofQkemuPo#t-enj]aJkCkWaKaxkWfkaKaefkfku5aekDkCkDaykWt-f6VYj]aeaekXkCadkXaxpJkDkCaKj]kWkDZ~aekWkWkCaKaJkDkWkXaxpIkDaeadkCkCaejEf6kCkCkCaxadf6kC"
                ),
                ModelUIImage(
                    "images/StoreInFeaturedBanner/storeInFeaturedBanner_2025_06_16_10_45_16_097_auto_quality.jpg",
                    ";xS5q*oeyDbIkXkBkWjZoLtSayV@f6f6j[ayj[ayysa}R5j[aJaeadbHWoIAkCozjukCe.kCayofm,ayX8aykWkCbIj[f6ozjZogbHaebHaej@WBSyj[niazjFj[fQaej[t8WqWAj?ayf6kCj[j]"
                ),
                ModelUIImage(
                    "images/StoreInFeaturedBanner/storeInFeaturedBanner_2025_06_16_10_45_16_217_auto_quality.jpg",
                    ";sMHJjWBt7j[xuWB%May%Mayj[fQayayayj[j[j[?bj[t7ayayofRjj[RjWBayj[j[ofofayWBWB~qj[M{j[RjayWBj[ayt7ofayayWBWBj[ofofRjayRjj[Rjj[WBWBWBofayayj[ayj[WBayay"
                ),
            )
        ),

        ModelUISuggestedMarket(
            "5018",
            background = ModelUIImage(
                "images/StoreInFeaturedBanner/storeInFeaturedBanner_background_2025_10_12_12_43_37_672_auto_quality.jpg",
                ";9RC0QsV?aSexuoyn*afRjxaW:kVoLRjayRkayoL~VSeIVsoM{WBbaofs:E2sCniR*xukBxaayR*xuoLM|WVt7oLWBRkoL^*S2M|xGM|afWBofoL9Gn%%2WVt7bGjaofR*D*s:xtR*xaoeRkRjjZ"
            ),
            title = "big market",
            subTitle = "big market",
            foreground = listOf(
                ModelUIImage(
                    "images/StoreInFeaturedBanner/storeInFeaturedBanner_2025_10_12_12_43_38_215_auto_quality.jpg",
                    ";bOVoR-m%%RlyER.o#kXR*xZayofaxj]aya}WCj[yER-ROs.V@s,j@aeogxYj?NIazaya}oKoef6yEWCV?ayWAjsWBjZj@Iqjut3ofayj[ayfRj[tmWVjFWVaeayf5j@WBS5j[WBj[oJoLjsazaz"
                ),
                ModelUIImage(
                    "images/StoreInFeaturedBanner/storeInFeaturedBanner_2025_10_12_12_43_38_421_auto_quality.jpg",
                    ";ZNvV$=|?^Jn%gSNozs.RP%#R*V@n%WBjZoLa}bH.mS4Mxs.RPoKjGWWkWVtf6bbofoLWqWBoKjZ%gWBRjafWBoKaxaykCV@t7WUWVW;j[oLayfkxaWBWVjFj[WpayoLj[R5ofkWWVj[jZj[bHf6"
                ),
                ModelUIImage(
                    "images/StoreInFeaturedBanner/storeInFeaturedBanner_2025_10_12_12_43_38_529_auto_quality.jpg",
                    ";mNv}Kxu_NRjadaxIUt7ogo#WCoJayfkj[ayj[ayJXazwaofbIazW=WBjYslayWrfRayjtj?j[fk-oayIpj[oLj@s:ayayWBj[kCj[ayayayfQj[-oWBM|fRj[j[ofayayWYj[jYj[fkaya}ayjs"
                ),
                ModelUIImage(
                    "images/StoreInFeaturedBanner/storeInFeaturedBanner_2025_10_12_12_43_38_622_auto_quality.jpg",
                    ";RPZJj-;.TD*RO-:RjRjog%gRjV@t7ayWBofoeay.9RjMd%LozM{ozoyWBM{t6j[R*ofofWBWBj[xuWBWBjskCkCaej@j[RPt7ozR*jZoeayayj]aKaykCa#aej[ayf6j[ofWBoeoeWBafkCj[ay"
                ),
                ModelUIImage(
                    "images/StoreInFeaturedBanner/storeInFeaturedBanner_2025_10_12_12_43_38_787_auto_quality.jpg",
                    ";NP6:K%g~VV@IUxuM|M|%M^+aeD*Rkt7ofofoeWBxuf7RjofoLV[ofofRjIVayxtofRkjsayWBoL?HWBIVj]t7WBoet6WBD*bH%LxaR*RjWBkCof%LaxM|R*oft7ayWCfQt7t7RjRjofoff6j[a}"
                ),
            )
        ),
    ),  id = "121"
)

val testCenteredOverlapCarouselData = listOf(
    ModelUIImage(
        "images/StoreInFeaturedBanner/storeInFeaturedBanner_2025_10_12_12_43_38_215_auto_quality.jpg",
        ";bOVoR-m%%RlyER.o#kXR*xZayofaxj]aya}WCj[yER-ROs.V@s,j@aeogxYj?NIazaya}oKoef6yEWCV?ayWAjsWBjZj@Iqjut3ofayj[ayfRj[tmWVjFWVaeayf5j@WBS5j[WBj[oJoLjsazaz"
    ),
    ModelUIImage(
        "images/StoreInFeaturedBanner/storeInFeaturedBanner_2025_10_12_12_43_38_421_auto_quality.jpg",
        ";ZNvV$=|?^Jn%gSNozs.RP%#R*V@n%WBjZoLa}bH.mS4Mxs.RPoKjGWWkWVtf6bbofoLWqWBoKjZ%gWBRjafWBoKaxaykCV@t7WUWVW;j[oLayfkxaWBWVjFj[WpayoLj[R5ofkWWVj[jZj[bHf6"
    ),
    ModelUIImage(
        "images/StoreInFeaturedBanner/storeInFeaturedBanner_2025_10_12_12_43_38_529_auto_quality.jpg",
        ";mNv}Kxu_NRjadaxIUt7ogo#WCoJayfkj[ayj[ayJXazwaofbIazW=WBjYslayWrfRayjtj?j[fk-oayIpj[oLj@s:ayayWBj[kCj[ayayayfQj[-oWBM|fRj[j[ofayayWYj[jYj[fkaya}ayjs"
    ),
    ModelUIImage(
        "images/StoreInFeaturedBanner/storeInFeaturedBanner_2025_10_12_12_43_38_622_auto_quality.jpg",
        ";RPZJj-;.TD*RO-:RjRjog%gRjV@t7ayWBofoeay.9RjMd%LozM{ozoyWBM{t6j[R*ofofWBWBj[xuWBWBjskCkCaej@j[RPt7ozR*jZoeayayj]aKaykCa#aej[ayf6j[ofWBoeoeWBafkCj[ay"
    ),
    ModelUIImage(
        "images/StoreInFeaturedBanner/storeInFeaturedBanner_2025_10_12_12_43_38_787_auto_quality.jpg",
        ";NP6:K%g~VV@IUxuM|M|%M^+aeD*Rkt7ofofoeWBxuf7RjofoLV[ofofRjIVayxtofRkjsayWBoL?HWBIVj]t7WBoet6WBD*bH%LxaR*RjWBkCof%LaxM|R*oft7ayWCfQt7t7RjRjofoff6j[a}"
    ),
)

val testModelUIMarketContainer = ModelUIMarketContainer(
    stores = listOf(
        ModelUIMarketStore(
            id = "529",
            name = "ROASTERY",
            address = "Umm Al Fahem, Al Shagour",
            rating = null,
            isNew = true,
            status = StoreOperatingTimeStatus.OPENING_SOON,
            image = ModelUIImage(
                "images/StoreInFeaturedBanner/storeInFeaturedBanner_2025_10_12_12_43_38_215_auto_quality.jpg",
                ";bOVoR-m%%RlyER.o#kXR*xZayofaxj]aya}WCj[yER-ROs.V@s,j@aeogxYj?NIazaya}oKoef6yEWCV?ayWAjsWBjZj@Iqjut3ofayj[ayfRj[tmWVjFWVaeayf5j@WBS5j[WBj[oJoLjsazaz"
            ),
            labels = listOf("Free", "10% Off")
        ),

        ModelUIMarketStore(
            id = "172",
            name = "Stekerz",
            address = "Al-Ayou, Al-Jdoua Junction",
            rating = ModelUIRating("4.3", "100+"),
            isNew = false,
            status = StoreOperatingTimeStatus.CLOSING_SOON,
            image = ModelUIImage(
                "images/Market/Icons/17220231203104434.jpg",
                null
            ),
            labels = emptyList()
        ),

        ModelUIMarketStore(
            id = "5127",
            name = "Newone",
            address = "Umm al-Fahem",
            rating = ModelUIRating("4.3", "100+"),
            isNew = false,
            status = StoreOperatingTimeStatus.CLOSING_SOON,
            image = ModelUIImage(
                "images/Market/icons/markets512720250411171725_original.jpg",
                ";iS5^tkCyCofx[a}ozjFoJozoLayayayj[ayayaz.layR5ayRPjZV@bbW=V@WXf+jsj[jFkCj[o0rCjtXTayX9kCkCf5jFkXjYjFkBayW=jFf6bHKPbHr=j@n\$f6j[fkj[r=kCbbafazn\$bHa|f6"
            ),
            labels = emptyList()
        ),

        ), id = "529", name = "Roastery"
)

val testModelUIMarketSponsoredContainer = ModelUIMarketSponsoredContainer(
    stores = listOf(
        ModelUIMarketStore(
            id = "529",
            name = "ROASTERY",
            address = "Umm Al Fahem, Al Shagour",
            rating = null,
            isNew = false,
            status = StoreOperatingTimeStatus.OPENING_SOON,
            image = ModelUIImage(
                "images/StoreInFeaturedBanner/storeInFeaturedBanner_2025_10_12_12_43_38_215_auto_quality.jpg",
                ";bOVoR-m%%RlyER.o#kXR*xZayofaxj]aya}WCj[yER-ROs.V@s,j@aeogxYj?NIazaya}oKoef6yEWCV?ayWAjsWBjZj@Iqjut3ofayj[ayfRj[tmWVjFWVaeayf5j@WBS5j[WBj[oJoLjsazaz"
            ),
            labels = listOf("Free", "10% Off")
        ),

        ModelUIMarketStore(
            id = "172",
            name = "Stekerz",
            address = "Al-Ayou, Al-Jdoua Junction",
            rating = null,
            isNew = false,
            status = StoreOperatingTimeStatus.CLOSING_SOON,
            image = ModelUIImage(
                "images/Market/Icons/17220231203104434.jpg",
                null
            ),
            labels = emptyList()
        ),

        ModelUIMarketStore(
            id = "5127",
            name = "Newone",
            address = "Umm al-Fahem",
            rating = ModelUIRating("4.3", "100+"),
            isNew = false,
            status = StoreOperatingTimeStatus.CLOSING_SOON,
            image = ModelUIImage(
                "images/Market/icons/markets512720250411171725_original.jpg",
                ";iS5^tkCyCofx[a}ozjFoJozoLayayayj[ayayaz.layR5ayRPjZV@bbW=V@WXf+jsj[jFkCj[o0rCjtXTayX9kCkCf5jFkXjYjFkBayW=jFf6bHKPbHr=j@n\$f6j[fkj[r=kCbbafazn\$bHa|f6"
            ),
            labels = emptyList()
        ),

        ), id = "591", name = "Roastery Sponsered"
)


val testModelUIMarketHighlightContainer = ModelUIMarketHighlightContainer(
    stores = listOf(
        ModelUIMarketStore(
            id = "529",
            name = "ROASTERY",
            address = "Umm Al Fahem, Al Shagour",
            rating = null,
            isNew = true,
            status = StoreOperatingTimeStatus.OPENING_SOON,
            image = ModelUIImage(
                "images/StoreInFeaturedBanner/storeInFeaturedBanner_2025_10_12_12_43_38_215_auto_quality.jpg",
                ";bOVoR-m%%RlyER.o#kXR*xZayofaxj]aya}WCj[yER-ROs.V@s,j@aeogxYj?NIazaya}oKoef6yEWCV?ayWAjsWBjZj@Iqjut3ofayj[ayfRj[tmWVjFWVaeayf5j@WBS5j[WBj[oJoLjsazaz"
            ),
            labels = listOf("Free", "10% Off")
        ),

        ModelUIMarketStore(
            id = "172",
            name = "Stekerz",
            address = "Al-Ayou, Al-Jdoua Junction",
            rating = ModelUIRating("4.3", "100+"),
            isNew = false,
            status = StoreOperatingTimeStatus.CLOSING_SOON,
            image = ModelUIImage(
                "images/Market/Icons/17220231203104434.jpg",
                null
            ),
            labels = emptyList()
        ),

        ModelUIMarketStore(
            id = "5127",
            name = "Newone",
            address = "Umm al-Fahem",
            rating = ModelUIRating("4.3", "100+"),
            isNew = false,
            status = StoreOperatingTimeStatus.CLOSING_SOON,
            image = ModelUIImage(
                "images/Market/icons/markets512720250411171725_original.jpg",
                ";iS5^tkCyCofx[a}ozjFoJozoLayayayj[ayayaz.layR5ayRPjZV@bbW=V@WXf+jsj[jFkCj[o0rCjtXTayX9kCkCf5jFkXjYjFkBayW=jFf6bHKPbHr=j@n\$f6j[fkj[r=kCbbafazn\$bHa|f6"
            ),
            labels = emptyList()
        ),

        ), id = "591", name = "Highlight",
    backgroundColor = "#FFFFFF",
    image = ModelUIImage(
        "images/Market/icons/markets523020250320222356_original.jpg",
        ";XG,Uqa$={j@-qof#XoI%\$irago~j[WGf6bej[nOI8aybvayNLayXAbbnO%\$j@sVaexabIsSa{tlVykBoMkCkCaeoynioc%Nj[oMflofoJoda}g3H?aySijYWobbXSW;aJo#WCn#jsW;a{jYoLjb"
    )
)


val testMarketStore = ModelUIMarketStore(
    id = "5127",
    name = "Newone",
    address = "Umm al-Fahem",
    rating = ModelUIRating("4.3", "100+"),
    isNew = false,
    status = StoreOperatingTimeStatus.CLOSING_SOON,
    image = ModelUIImage(
        "images/Market/icons/markets512720250411171725_original.jpg",
        ";iS5^tkCyCofx[a}ozjFoJozoLayayayj[ayayaz.layR5ayRPjZV@bbW=V@WXf+jsj[jFkCj[o0rCjtXTayX9kCkCf5jFkXjYjFkBayW=jFf6bHKPbHr=j@n\$f6j[fkj[r=kCbbafazn\$bHa|f6"
    ),
    labels = emptyList()
)

val testModelUIPromotedBannerContainer = ModelUIPromotedBannerContainer(
    "125",
    category = HomeCategory.GeneralPromotedBanner,
    id="12",
    image = ModelUIImage(
        "images/PromotedBanner/promotedBanner_0ZGGQTS6_2025_11_02_11_50_33_435_original.jpg",
        ";oH]^BoL0yayNaayozbHof1Ha|$*a|xajtnijtWBIUay%2j[S2j[R*aybHRPfkozf6WBayn%fQofx]fQV@fjV@azozj[ayxGfQR*fkbva{aeayaybbfQR*azs:j[ofj[aeW;fkjZayoLjtWBf6f*"
    )
)


val testModelUIMarketVerticalContainer = ModelUIMarketVerticalContainer(
    originalStores = listOf(
        ModelUIMarketStore(
            id = "529",
            name = "ROASTERY",
            address = "Umm Al Fahem, Al Shagour",
            rating = null,
            isNew = true,
            status = StoreOperatingTimeStatus.OPENING_SOON,
            image = ModelUIImage(
                "images/StoreInFeaturedBanner/storeInFeaturedBanner_2025_10_12_12_43_38_215_auto_quality.jpg",
                ";bOVoR-m%%RlyER.o#kXR*xZayofaxj]aya}WCj[yER-ROs.V@s,j@aeogxYj?NIazaya}oKoef6yEWCV?ayWAjsWBjZj@Iqjut3ofayj[ayfRj[tmWVjFWVaeayf5j@WBS5j[WBj[oJoLjsazaz"
            ),
            labels = listOf("Free", "10% Off")
        ),

        ModelUIMarketStore(
            id = "172",
            name = "Stekerz",
            address = "Al-Ayou, Al-Jdoua Junction",
            rating = ModelUIRating("4.3", "100+"),
            isNew = false,
            status = StoreOperatingTimeStatus.CLOSING_SOON,
            image = ModelUIImage(
                "images/Market/Icons/17220231203104434.jpg",
                null
            ),
            labels = emptyList()
        ),

        ModelUIMarketStore(
            id = "5127",
            name = "Newone",
            address = "Umm al-Fahem",
            rating = ModelUIRating("4.3", "100+"),
            isNew = false,
            status = StoreOperatingTimeStatus.CLOSING_SOON,
            image = ModelUIImage(
                "images/Market/icons/markets512720250411171725_original.jpg",
                ";iS5^tkCyCofx[a}ozjFoJozoLayayayj[ayayaz.layR5ayRPjZV@bbW=V@WXf+jsj[jFkCj[o0rCjtXTayX9kCkCf5jFkXjYjFkBayW=jFf6bHKPbHr=j@n\$f6j[fkj[r=kCbbafazn\$bHa|f6"
            ),
            labels = emptyList()
        ),

        ModelUIMarketStore(
            id = "517",
            name = "Newone517",
            address = "Umm al-Fahem",
            rating = ModelUIRating("4.3", "100+"),
            isNew = false,
            status = StoreOperatingTimeStatus.BUSY,
            image = ModelUIImage(
                "images/Market/icons/markets512720250411171725_original.jpg",
                ";iS5^tkCyCofx[a}ozjFoJozoLayayayj[ayayaz.layR5ayRPjZV@bbW=V@WXf+jsj[jFkCj[o0rCjtXTayX9kCkCf5jFkXjYjFkBayW=jFf6bHKPbHr=j@n\$f6j[fkj[r=kCbbafazn\$bHa|f6"
            ),
            labels = emptyList()
        ),


        ModelUIMarketStore(
            id = "1172",
            name = "Stekerz",
            address = "Al-Ayou, Al-Jdoua Junction",
            rating = ModelUIRating("4.3", "100+"),
            isNew = false,
            status = StoreOperatingTimeStatus.CLOSING_SOON,
            image = ModelUIImage(
                "images/Market/Icons/17220231203104434.jpg",
                null
            ),
            labels = emptyList()
        ),

        ModelUIMarketStore(
            id = "5029",
            name = "ROASTERY",
            address = "Umm Al Fahem, Al Shagour",
            rating = null,
            isNew = true,
            status = StoreOperatingTimeStatus.OPENING_SOON,
            image = ModelUIImage(
                "images/StoreInFeaturedBanner/storeInFeaturedBanner_2025_10_12_12_43_38_215_auto_quality.jpg",
                ";bOVoR-m%%RlyER.o#kXR*xZayofaxj]aya}WCj[yER-ROs.V@s,j@aeogxYj?NIazaya}oKoef6yEWCV?ayWAjsWBjZj@Iqjut3ofayj[ayfRj[tmWVjFWVaeayf5j@WBS5j[WBj[oJoLjsazaz"
            ),
            labels = listOf("Free", "10% Off")
        ),

        ModelUIMarketStore(
            id = "1702",
            name = "Stekerz",
            address = "Al-Ayou, Al-Jdoua Junction",
            rating = ModelUIRating("4.3", "100+"),
            isNew = false,
            status = StoreOperatingTimeStatus.CLOSING_SOON,
            image = ModelUIImage(
                "images/Market/Icons/17220231203104434.jpg",
                null
            ),
            labels = emptyList()
        ),

        ModelUIMarketStore(
            id = "51027",
            name = "Newone",
            address = "Umm al-Fahem",
            rating = ModelUIRating("4.3", "100+"),
            isNew = false,
            status = StoreOperatingTimeStatus.CLOSING_SOON,
            image = ModelUIImage(
                "images/Market/icons/markets512720250411171725_original.jpg",
                ";iS5^tkCyCofx[a}ozjFoJozoLayayayj[ayayaz.layR5ayRPjZV@bbW=V@WXf+jsj[jFkCj[o0rCjtXTayX9kCkCf5jFkXjYjFkBayW=jFf6bHKPbHr=j@n\$f6j[fkj[r=kCbbafazn\$bHa|f6"
            ),
            labels = emptyList()
        ),

        ModelUIMarketStore(
            id = "11702",
            name = "Stekerz",
            address = "Al-Ayou, Al-Jdoua Junction",
            rating = ModelUIRating("4.3", "100+"),
            isNew = false,
            status = StoreOperatingTimeStatus.CLOSING_SOON,
            image = ModelUIImage(
                "images/Market/Icons/17220231203104434.jpg",
                null
            ),
            labels = emptyList()
        ),

        ModelUIMarketStore(
            id = "151027",
            name = "Newone",
            address = "Umm al-Fahem",
            rating = ModelUIRating("4.3", "100+"),
            isNew = false,
            status = StoreOperatingTimeStatus.CLOSING_SOON,
            image = ModelUIImage(
                "images/Market/icons/markets512720250411171725_original.jpg",
                ";iS5^tkCyCofx[a}ozjFoJozoLayayayj[ayayaz.layR5ayRPjZV@bbW=V@WXf+jsj[jFkCj[o0rCjtXTayX9kCkCf5jFkXjYjFkBayW=jFf6bHKPbHr=j@n\$f6j[fkj[r=kCbbafazn\$bHa|f6"
            ),
            labels = emptyList()
        ),

        ), id = "591", name = "Highlight"
)
