package com.ozantopuz.rijksmuseumapp.di

import com.ozantopuz.rijksmuseumapp.data.mapper.CollectionDomainMapper
import com.ozantopuz.rijksmuseumapp.domain.mapper.CollectionViewItemMapper
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
class MapperModule {

    @Provides
    fun providesCollectionDomainMapper(): CollectionDomainMapper = CollectionDomainMapper()

    @Provides
    fun providesCollectionViewItemMapper(): CollectionViewItemMapper = CollectionViewItemMapper()
}