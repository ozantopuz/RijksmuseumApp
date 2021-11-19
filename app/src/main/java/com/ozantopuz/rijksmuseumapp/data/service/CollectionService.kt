package com.ozantopuz.rijksmuseumapp.data.service

import com.ozantopuz.rijksmuseumapp.data.entity.CollectionResponse
import retrofit2.http.GET
import retrofit2.http.Query
import retrofit2.http.QueryMap

interface CollectionService {

    @GET("collection")
    suspend fun getCollection(
        @QueryMap queryMap: Map<String, String>
    ): CollectionResponse
}