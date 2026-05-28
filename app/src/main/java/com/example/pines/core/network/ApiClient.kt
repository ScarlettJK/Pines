package com.example.pines.core.network

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object ApiClient {

    private const val BASE_URL = "https://api.unsplash.com/"
    const val CLIENT_ID = "yiEe-snJjxWe57fHrPoj5ELcjP6Gep9OGhag4YPiTJg"

    private val loggin = HttpLoggingInterceptor().apply {
        level = HttpLoggingInterceptor.Level.BODY
    }

    private val client = OkHttpClient.Builder()
        .addInterceptor (loggin)
        .build()

    val PinesApi: PinesAPI by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(client)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(PinesAPI::class.java)

    }

}