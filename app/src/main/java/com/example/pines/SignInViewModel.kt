package com.example.pines


import android.util.Patterns
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.pines.core.AuthRepository
import com.example.pines.core.ResponseService
import com.google.firebase.auth.FirebaseUser
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class SignInViewModel: ViewModel() {
    val repository = AuthRepository()

    //fun requestSignUp(email: String, password: String) {
        private val _signInState = MutableStateFlow<ResponseService<FirebaseUser>?>(null)
        val SignInState: StateFlow<ResponseService<FirebaseUser>?> = _signInState.asStateFlow()
        fun validateEmail(email: String): String? {
            if (email.isBlank()) return  "El correo es requerido"
            if (!Patterns.EMAIL_ADDRESS.matcher(email).matches())
                return  "Correo invalido"
            return null
        }

    fun validatePassword(password: String): String? {
        if (password.isBlank()) return "La contraseña es requerida"
        if (password.length < 8) return "Mínimo 8 caracteres"
        return null
    }

    fun isLoginFormValid(email: String, password: String): Boolean {
        return validateEmail(email) == null &&
            validatePassword(password) == null
        }


    // --- Operación de login ---
    fun requestLogin(email: String, password: String) {
        viewModelScope.launch {
                /*
                val result = repository.requestSignUp(email, password)
                result?.let { user ->
                    Log.i("Session", "Se ha creado el usuario ${user.uid}")
                } ?: run {
                    Log.e("Error", "Hubo un error al crear al usuario")
                }
                 */
            _signInState.value = ResponseService.Loading
            _signInState.value = repository.requestLogin(email, password)
        }
    }
}