package com.ozantopuz.rijksmuseumapp.ui.entity

import com.ozantopuz.rijksmuseumapp.util.entity.ViewItem

data class ArtObjectViewItem(
    val id: String,
    val objectNumber: String,
    val title: String,
    val principalOrFirstMaker: String,
    val longTitle: String,
    val webImageUrl: String,
    val headerImageUrl: String,
    val productionPlaces: String
) : ViewItem