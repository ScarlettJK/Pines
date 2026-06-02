package com.example.pines.core.repositories

import com.example.pines.core.ResponseService
import com.example.pines.core.model.Pines
import com.example.pines.core.network.ApiClient
import com.example.pines.core.network.PinesService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class PinesRepository: PinesService {
    private val api = ApiClient.PinesApi

    override suspend fun getPines(limit: Int): ResponseService<List<Pines>> =
        withContext(Dispatchers.IO) {
            try {
                val response = api.getPines(
                    clientId = ApiClient.CLIENT_ID,
                    limit = limit
                )
                if (response.isSuccessful) {
                    val body = response.body()
                    if (body != null) {
                        ResponseService.Success(body)
                    } else {
                        ResponseService.Error("Respuesta vacía del servidor")
                    }
                } else {
                    ResponseService.Error("Error ${response.code()}: ${response.message()}")
                }
            } catch (e: Exception) {
                ResponseService.Error(
                    "No se pudieron cargar las imagenes: ${e.localizedMessage}"
                )
            }
        }

}