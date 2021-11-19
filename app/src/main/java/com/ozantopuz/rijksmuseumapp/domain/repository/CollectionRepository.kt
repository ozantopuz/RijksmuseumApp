package com.ozantopuz.rijksmuseumapp.domain.repository

import com.ozantopuz.rijksmuseumapp.data.Result
import com.ozantopuz.rijksmuseumapp.domain.entity.Collection
import com.ozantopuz.rijksmuseumapp.domain.usecase.CollectionParams
import kotlinx.coroutines.flow.Flow

interface CollectionRepository {
    suspend fun fetchCollection(params: CollectionParams): Flow<Result<Collection>>
}