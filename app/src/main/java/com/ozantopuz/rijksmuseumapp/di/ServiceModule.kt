package com.ozantopuz.rijksmuseumapp.di

import com.ozantopuz.rijksmuseumapp.data.service.CollectionService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.create
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class ServiceModule {

    @Provides
    @Singleton
    fun provideCollectionService(retrofit: Retrofit): CollectionService = retrofit.create()
}