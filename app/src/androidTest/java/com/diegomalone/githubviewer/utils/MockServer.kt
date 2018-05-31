package com.diegomalone.githubviewer.utils

import android.content.Context
import com.diegomalone.githubviewer.di.module.ApiModule
import com.diegomalone.githubviewer.utils.Utils.readFileFromAssets
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer

class MockServer {

    companion object {
        private const val MOCK_WEB_SERVER_PORT = 8000
    }

    private var mockWebServer = MockWebServer()
    var context: Context? = null

    init {
        ApiModule.apiUrl = "http://localhost:$MOCK_WEB_SERVER_PORT/"
    }

    fun startTestCase() {
        mockWebServer = MockWebServer()
        mockWebServer.start(MOCK_WEB_SERVER_PORT)
    }

    fun endTestCase() {
        mockWebServer.shutdown()
    }

    fun enqueue(responseCode: Int, responseBodyFileName: String? = null) {
        val mockResponse = MockResponse().setResponseCode(responseCode)

        responseBodyFileName?.let {
            mockResponse.setBody(readFileFromAssets(context, it))
        }

        mockWebServer.enqueue(mockResponse)
    }
}