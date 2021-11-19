package com.ozantopuz.rijksmuseumapp.data.mapper

import com.ozantopuz.rijksmuseumapp.data.entity.CollectionResponse
import com.ozantopuz.rijksmuseumapp.domain.entity.ArtObject
import com.ozantopuz.rijksmuseumapp.domain.entity.Collection
import com.ozantopuz.rijksmuseumapp.util.mapper.Mapper

class CollectionDomainMapper : Mapper<CollectionResponse, Collection> {

    override suspend fun map(item: CollectionResponse): Collection {
        val artObjects: ArrayList<ArtObject> = arrayListOf()
        item.artObjects?.forEach { response ->
            val artObject = ArtObject(
                id = response.id,
                objectNumber = response.objectNumber,
                title = response.title,
                principalOrFirstMaker = response.principalOrFirstMaker,
                longTitle = response.longTitle,
                webImageUrl = response.webImage?.url,
                headerImageUrl = response.headerImage?.url,
                productionPlaces = response.productionPlaces
            )
            artObjects.add(artObject)
        }
        return Collection(artObjects)
    }
}