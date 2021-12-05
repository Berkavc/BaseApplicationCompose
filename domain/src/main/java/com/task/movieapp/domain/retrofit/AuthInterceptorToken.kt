package com.task.movieapp.domain.retrofit

import com.task.movieapp.domain.API_KEY
import okhttp3.Interceptor
import okhttp3.Response

internal class AuthInterceptorToken() : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        var request = chain.request()
        val requestUrl = request.url().newBuilder().addQueryParameter("api_key", API_KEY).build()
        request = request.newBuilder()
            .url(requestUrl)
            .header("Content-Type", "application/json")
            .build()
        return chain.proceed(request)
    }
}