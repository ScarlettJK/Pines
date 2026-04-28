package com.example.pines.core

interface Login {
    suspend fun requestLogin(email: String, password: String): ResponseService
    suspend fun requestSignUp(email: String, password: String): ResponseService
}