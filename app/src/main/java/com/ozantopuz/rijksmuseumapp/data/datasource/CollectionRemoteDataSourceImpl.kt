package com.ozantopuz.rijksmuseumapp.data.datasource

import com.ozantopuz.rijksmuseumapp.data.Result
import com.ozantopuz.rijksmuseumapp.data.entity.CollectionResponse
import com.ozantopuz.rijksmuseumapp.data.mapper.CollectionDomainMapper
import com.ozantopuz.rijksmuseumapp.data.service.CollectionService
import com.ozantopuz.rijksmuseumapp.domain.entity.Collection
import com.ozantopuz.rijksmuseumapp.domain.usecase.CollectionParams
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class CollectionRemoteDataSourceImpl @Inject constructor(
    private val service: CollectionService,
    private val mapper: CollectionDomainMapper
) : CollectionRemoteDataSource {

    override suspend fun fetchCollection(params: CollectionParams): Flow<Result<Collection>> =
        flow<Result<Collection>> {
            val queryMap: HashMap<String, String> = hashMapOf()
            queryMap[PAGE_SIZE_KEY] = PAGE_SIZE_VALUE
            queryMap[PAGE_KEY] = params.page.toString()
            params.query?.let { queryMap.put(QUERY_KEY, it) }

            val response = service.getCollection(queryMap)
            val collection = mapper.map(response)

            emit(Result.Success(collection))
        }.catch { throwable ->
            emit(Result.Error(Exception(throwable)))
        }.flowOn(Dispatchers.IO)

    companion object {
        private const val PAGE_KEY = "p"
        private const val QUERY_KEY = "q"
        private const val PAGE_SIZE_KEY = "ps"
        private const val PAGE_SIZE_VALUE = "30"
    }
}
