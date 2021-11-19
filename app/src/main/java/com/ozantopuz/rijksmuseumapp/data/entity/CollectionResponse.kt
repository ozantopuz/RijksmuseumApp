package com.ozantopuz.rijksmuseumapp.data.entity

import com.ozantopuz.rijksmuseumapp.util.entity.RemoteDataSourceItem

data class CollectionResponse(
    val elapsedMilliseconds: Int? = null,
    val count: Int? = null,
    val artObjects: List<ArtObjectResponse>? = null
) : RemoteDataSourceItem