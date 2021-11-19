package com.ozantopuz.rijksmuseumapp.domain.mapper

import com.ozantopuz.rijksmuseumapp.domain.entity.Collection
import com.ozantopuz.rijksmuseumapp.ui.entity.ArtObjectViewItem
import com.ozantopuz.rijksmuseumapp.ui.entity.CollectionViewItem
import com.ozantopuz.rijksmuseumapp.util.extension.ignoreNull
import com.ozantopuz.rijksmuseumapp.util.mapper.Mapper

class CollectionViewItemMapper : Mapper<Collection, CollectionViewItem> {

    override suspend fun map(item: Collection): CollectionViewItem {
        val artObjects: ArrayList<ArtObjectViewItem> = arrayListOf()
        item.artObjects.forEach { response ->
            val artObject = ArtObjectViewItem(
                id = response.id.ignoreNull(),
                objectNumber = response.objectNumber.ignoreNull(),
                title = response.title.ignoreNull(),
                principalOrFirstMaker = response.principalOrFirstMaker.ignoreNull(),
                longTitle = response.longTitle.ignoreNull(),
                webImageUrl = response.webImageUrl.ignoreNull(),
                headerImageUrl = response.headerImageUrl.ignoreNull(),
                productionPlaces = response.productionPlaces?.joinToString(separator = ", ")
                    .ignoreNull()
            )
            artObjects.add(artObject)
        }
        return CollectionViewItem(artObjects)
    }
}