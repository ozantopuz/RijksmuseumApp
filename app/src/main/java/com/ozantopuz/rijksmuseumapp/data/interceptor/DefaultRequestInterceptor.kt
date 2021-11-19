package com.ozantopuz.rijksmuseumapp.data.interceptor

import okhttp3.HttpUrl
import okhttp3.Interceptor
import okhttp3.Request
import okhttp3.Response
import javax.inject.Inject

class DefaultRequestInterceptor @Inject constructor() : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        var request: Request = chain.request()
        val url: HttpUrl = request.url.newBuilder().addQueryParameter(KEY, API_KEY).build()
        request = request.newBuilder().addHeader(CONTENT_TYPE, JSON).url(url).build()
        return chain.proceed(request)
    }

    companion object {
        private const val CONTENT_TYPE = "Content-Type"
        private const val JSON = "application/json"
        private const val KEY = "key"
        private const val API_KEY = "RwBK1Rcl"
    }
}
