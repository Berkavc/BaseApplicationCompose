package com.sipay.baseapplicationcompose.retrofit

import com.google.gson.GsonBuilder
import com.sipay.baseapplicationcompose.retrofit.AuthInterceptorApiKey
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

class RetrofitClient {
    fun getRetrofitClient(url: String): Retrofit {
        val gsonBuilder = GsonBuilder()
        val gson = gsonBuilder.create()
        val client = OkHttpClient.Builder()
            .addInterceptor(
                AuthInterceptorApiKey()
            )
            .connectTimeout(120, TimeUnit.SECONDS)
            .callTimeout(120, TimeUnit.SECONDS)
            .writeTimeout(120, TimeUnit.SECONDS)
            .readTimeout(120, TimeUnit.SECONDS)
            .build()
        return Retrofit.Builder()
            .baseUrl(url)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .client(client)
            .build()
    }
}