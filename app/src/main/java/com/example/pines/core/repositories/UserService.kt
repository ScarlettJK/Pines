package com.example.pines.core.repositories

import com.example.pines.core.ResponseService
import com.example.pines.onboarding.personal.model.UserProfile

interface UserService {

    suspend fun saveUserInfo(userProfile: UserProfile): ResponseService<Unit> //Recibe el modelo de UserProfile

}