package com.ozantopuz.rijksmuseumapp.shared.dispatcher

import okhttp3.mockwebserver.Dispatcher
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.RecordedRequest
import javax.net.ssl.HttpsURLConnection

object ErrorDispatcher : Dispatcher() {

    override fun dispatch(request: RecordedRequest): MockResponse {
        return MockResponse().setResponseCode(HttpsURLConnection.HTTP_NOT_FOUND)
    }
}
