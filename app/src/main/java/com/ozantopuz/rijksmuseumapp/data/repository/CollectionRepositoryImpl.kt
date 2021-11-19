package com.ozantopuz.rijksmuseumapp.data.repository

import com.ozantopuz.rijksmuseumapp.data.Result
import com.ozantopuz.rijksmuseumapp.data.datasource.CollectionRemoteDataSource
import com.ozantopuz.rijksmuseumapp.domain.entity.Collection
import com.ozantopuz.rijksmuseumapp.domain.repository.CollectionRepository
import com.ozantopuz.rijksmuseumapp.domain.usecase.CollectionParams
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class CollectionRepositoryImpl @Inject constructor(
    private val remoteDataSource: CollectionRemoteDataSource
) : CollectionRepository {

    override suspend fun fetchCollection(params: CollectionParams): Flow<Result<Collection>> =
        remoteDataSource.fetchCollection(params)
}