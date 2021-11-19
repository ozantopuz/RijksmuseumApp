package com.ozantopuz.rijksmuseumapp.shared.dispatcher

import okhttp3.mockwebserver.Dispatcher
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.RecordedRequest
import okhttp3.mockwebserver.SocketPolicy
import java.util.concurrent.TimeUnit

object TimeoutDispatcher : Dispatcher() {

    private const val BYTES_PER_PERIOD = 1024L
    private const val PERIOD = 2L

    override fun dispatch(request: RecordedRequest): MockResponse {
        return MockResponse()
            .setSocketPolicy(SocketPolicy.NO_RESPONSE)
            .throttleBody(BYTES_PER_PERIOD, PERIOD, TimeUnit.SECONDS)
    }
}
