package com.ozantopuz.rijksmuseumapp.util.usecase

import com.ozantopuz.rijksmuseumapp.data.Result
import kotlinx.coroutines.flow.Flow

interface UseCase {

    @FunctionalInterface
    interface FlowUseCase<in P, out T> : UseCase where P : Params {
        suspend fun execute(params: P): Flow<Result<T>>
    }
}

abstract class Params