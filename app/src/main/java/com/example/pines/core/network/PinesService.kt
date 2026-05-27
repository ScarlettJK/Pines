package com.example.pines.core.network

import com.example.pines.core.ResponseService
import com.example.pines.core.model.Pines

interface PinesService {

    suspend fun getPines(limit: Int = 20): ResponseService<List<Pines>>

}