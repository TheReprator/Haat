package dev.reprator.haat.features.restaurant2pane.restaurants


data class ModelUIBannerContainer(val interval: Long,val imageBannerList: List<ModelUIBanner>)

data class ModelUIBanner(val id: String, val name: String, val image: ModelUIImage)


data class ModelUIImage(val imageUrl: String, val blurHash: String)


data class ModelUITagContainer(val name: String, val imageList: List<ModelUITag>)

data class ModelUITag(val id: String, val name: String, val image: ModelUIImage)



val testBannerContainer = ModelUIBannerContainer(6, listOf(
    ModelUIBanner("1","Breakfast", image= ModelUIImage("images/TagInventory/30_20250612080729_original.jpg", ";OOgKNj[D%j[4nj[00f600aefkj[j[j[fQj[ayay4nj[ayj[oLayt7ayt7t7j[ayj[aya|ayayay4Tfkofj[offQt7ayt7t7fkaej[WBfQayayfQ4nfQofj[ofayt7fQt7offQayj[ayfQfQfQay")),
    ModelUIBanner("2","Lunch", image= ModelUIImage("images/Markets/MarketTags/38720240709111418_original.jpg", ";GMp5=o1RYt4tSjbtlV_tkw]j?W;f6WEbHjsazoJ9Saz?sWEyCodtloxo#nkWEWEj@odf7o0ocWX_IkAOHj@o~WEo}WEtQsmodoxWERnf6jtafj[SRWEyBj@tRoxtRjtozs.V_RnkAoeocfkocj@")),
    ModelUIBanner("3","Dinner", image= ModelUIImage("images/Markets/MarketTags/37720240709111640_original.jpg", ";yLN#sXA_4%0?bWBxuRjf6bIoJj?WDjsfPWUj[WCb2t2obNKWAjsWBofj@x[WCf8s,WXayWBWVWB.7WCe:j=RkjsRjf7RkV@j[bba#oeayWXj[j@%2ayWVjtWBWVWBj@j?IWoen#R+j]oft6fRog")),
))

val testTagContainer = ModelUITagContainer("What Are You Looking For?🤔", listOf(
    ModelUITag("506","MEAT & FISHa", image= ModelUIImage("images/TagInventory/30_20250612080729_original.jpg", ";xOV}ss.?^SLx^n+xba#oMxaa#R*j?afoLoKWVay?^WVaLs.MxbHRPj[R*R-j@o0jZj[WpWXjtj[x]bGM_n+WAWoogjst7jZj?a{WWoeoLf8oeayxboKNFWWogoJt7azWCoJWVWXoeoLayayazf6")),
    ModelUITag("387","MEAT & FISH", image= ModelUIImage("images/Markets/MarketTags/38720240709111418_original.jpg", ";xOffLs.?^SLx^n+xba#oMxaa#R*j?afoLoKWVay?^WVaLs.MxbHRPj@R*R,j@o0jZj[WpWXjtj[x]bGM_n+WAWoogjst7jZj?a{WWoeoLf8oeayxboKNFWVogoJt7azWCocWVWXoeoLayayazf6")),
    ModelUITag("377","ROASTERY", image= ModelUIImage("images/Markets/MarketTags/37720240709111640_original.jpg", ";wOC?NWV_NnjtmoexuW=oft7j[jYayS2f7jZj[fQ.To2oef+MxazRijZV@WXafWBkBoKj[j[ayj[%gj@MxWWaKayX8f6t7s.fkR+j@ofWCbHjtfPxbbHRPjZkCayt7j[j[ayjZj[ayj[j[aya|WC")),
))