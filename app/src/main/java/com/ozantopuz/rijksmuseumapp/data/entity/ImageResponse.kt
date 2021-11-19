package com.ozantopuz.rijksmuseumapp.data.entity

import com.ozantopuz.rijksmuseumapp.util.entity.RemoteDataSourceItem

data class ImageResponse(
    val guid: String? = null,
    val offsetPercentageX: Int? = null,
    val offsetPercentageY: Int? = null,
    val width: Int? = null,
    val height: Int? = null,
    val url: String? = null
): RemoteDataSourceItem