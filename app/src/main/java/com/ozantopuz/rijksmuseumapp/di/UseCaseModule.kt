package com.ozantopuz.rijksmuseumapp.di

import com.ozantopuz.rijksmuseumapp.domain.usecase.GetCollectionUseCase
import com.ozantopuz.rijksmuseumapp.util.usecase.UseCase
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class UseCaseModule {

    @Binds
    abstract fun bindGetCollectionUseCase(useCase: GetCollectionUseCase): UseCase
}