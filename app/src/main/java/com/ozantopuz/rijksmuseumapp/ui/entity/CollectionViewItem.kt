package com.ozantopuz.rijksmuseumapp.ui.entity

import com.ozantopuz.rijksmuseumapp.util.entity.ViewItem

data class CollectionViewItem(
    val artObjects: List<ArtObjectViewItem>
) : ViewItem