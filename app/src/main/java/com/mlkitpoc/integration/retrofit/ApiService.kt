package com.mlkitpoc.integration.retrofit

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object ApiService {

    fun getApiClient(): ApiClient {
        val okClientBuilder = OkHttpClient.Builder()
        val httpLoggingInterceptor = HttpLoggingInterceptor()
        httpLoggingInterceptor.level = HttpLoggingInterceptor.Level.BODY //LOGS
        okClientBuilder.interceptors().add(httpLoggingInterceptor)
        val retrofit = Retrofit.Builder()
                .baseUrl("https://digital.dmart.in/")
                .client(okClientBuilder.build())
                .addConverterFactory(GsonConverterFactory.create())
                .build()

        return retrofit.create(ApiClient::class.java)
    }
}