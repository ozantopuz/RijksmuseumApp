package com.ozantopuz.rijksmuseumapp.domain.entity

import com.ozantopuz.rijksmuseumapp.util.entity.DomainItem

data class ArtObject(
    val id: String? = null,
    val objectNumber: String? = null,
    val title: String? = null,
    val principalOrFirstMaker: String? = null,
    val longTitle: String? = null,
    val webImageUrl: String? = null,
    val headerImageUrl: String? = null,
    val productionPlaces: List<String>? = null
) : DomainItem