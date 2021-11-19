package com.ozantopuz.rijksmuseumapp.domain.usecase

import com.ozantopuz.rijksmuseumapp.domain.mapper.CollectionViewItemMapper
import com.ozantopuz.rijksmuseumapp.domain.repository.CollectionRepository
import com.ozantopuz.rijksmuseumapp.ui.entity.CollectionViewItem
import com.ozantopuz.rijksmuseumapp.data.Result
import com.ozantopuz.rijksmuseumapp.data.succeeded
import com.ozantopuz.rijksmuseumapp.util.usecase.Params
import com.ozantopuz.rijksmuseumapp.util.usecase.UseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import java.lang.Exception
import javax.inject.Inject

class GetCollectionUseCase @Inject constructor(
    private val repository: CollectionRepository,
    private val mapper: CollectionViewItemMapper
) : UseCase.FlowUseCase<CollectionParams, CollectionViewItem> {

    override suspend fun execute(params: CollectionParams): Flow<Result<CollectionViewItem>> {
        return repository.fetchCollection(params)
            .flatMapLatest { result ->
                flow {
                    if (result.succeeded) {
                        result as Result.Success
                        val viewItem = mapper.map(result.data)
                        emit(Result.Success(viewItem))
                        return@flow
                    }
                    result as Result.Error
                    emit(Result.Error(result.exception))
                }
            }.catch { throwable ->
                emit(Result.Error(Exception(throwable)))
            }.flowOn(Dispatchers.IO)
    }
}

data class CollectionParams(
    val page: Int,
    val query: String?
) : Params()
