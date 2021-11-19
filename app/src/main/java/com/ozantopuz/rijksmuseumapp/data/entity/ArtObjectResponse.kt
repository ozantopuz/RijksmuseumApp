package com.ozantopuz.rijksmuseumapp.data.entity

import com.ozantopuz.rijksmuseumapp.util.entity.RemoteDataSourceItem

data class ArtObjectResponse(
    val id: String? = null,
    val objectNumber: String? = null,
    val title: String? = null,
    val hasImage: Boolean? = null,
    val principalOrFirstMaker: String? = null,
    val longTitle: String? = null,
    val showImage: Boolean? = null,
    val permitDownload: Boolean? = null,
    val webImage: ImageResponse? = null,
    val headerImage: ImageResponse? = null,
    val productionPlaces: List<String>? = null
): RemoteDataSourceItem