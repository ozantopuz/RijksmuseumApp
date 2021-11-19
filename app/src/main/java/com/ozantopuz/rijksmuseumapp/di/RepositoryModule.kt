package com.ozantopuz.rijksmuseumapp.di

import com.ozantopuz.rijksmuseumapp.data.repository.CollectionRepositoryImpl
import com.ozantopuz.rijksmuseumapp.domain.repository.CollectionRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds
    abstract fun bindCollectionRepository(repository: CollectionRepositoryImpl): CollectionRepository
}