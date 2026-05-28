package com.example.pines.core.network

import  com.example.pines.core.model.Pines
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface PinesAPI {
    @GET("photos")
    suspend fun getPines(
        @Query("client_id") clientId: String,
        @Query("format") format: String = "json",
        @Query("per_page") limit: Int = 20
        //): Response<List<Pines>>
    ): Response<Pines>
}