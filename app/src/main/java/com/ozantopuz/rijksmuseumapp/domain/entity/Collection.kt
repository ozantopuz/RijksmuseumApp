package com.ozantopuz.rijksmuseumapp.domain.entity

import com.ozantopuz.rijksmuseumapp.util.entity.DomainItem

data class Collection(
    val artObjects: List<ArtObject>
): DomainItem