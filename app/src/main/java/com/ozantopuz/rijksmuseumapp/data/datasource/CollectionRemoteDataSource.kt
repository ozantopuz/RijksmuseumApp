package com.ozantopuz.rijksmuseumapp.data.datasource

import com.ozantopuz.rijksmuseumapp.data.Result
import com.ozantopuz.rijksmuseumapp.domain.entity.Collection
import com.ozantopuz.rijksmuseumapp.domain.usecase.CollectionParams
import kotlinx.coroutines.flow.Flow

interface CollectionRemoteDataSource {
    suspend fun fetchCollection(params: CollectionParams): Flow<Result<Collection>>
}