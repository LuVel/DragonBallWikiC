package com.example.dragonballwikic.ui.auth

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.dragonballwikic.data.repository.AuthRepository
import com.google.firebase.auth.FirebaseUser
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

data class AuthUiState(
    val isLoading: Boolean = false,
    val user: FirebaseUser? = null,
    val errorMessage: String? = null,
    val isSuccess: Boolean = false
)

class AuthViewModel : ViewModel() {

    private val repository = AuthRepository()

    private val _uiState = MutableStateFlow(AuthUiState())
    val uiState: StateFlow<AuthUiState> = _uiState

    val isUserLoggedIn: Boolean
        get() = repository.currentUser != null

    fun login(email: String, password: String) {
        viewModelScope.launch {
            _uiState.value = AuthUiState(isLoading = true)
            val result = repository.login(email, password)
            result.fold(
                onSuccess = { user ->
                    _uiState.value = AuthUiState(user = user, isSuccess = true)
                },
                onFailure = { e ->
                    _uiState.value = AuthUiState(errorMessage = e.message)
                }
            )
        }
    }

    fun register(
        nombre: String,
        apellidoPaterno: String,
        apellidoMaterno: String,
        username: String,
        email: String,
        password: String
    ) {
        viewModelScope.launch {
            _uiState.value = AuthUiState(isLoading = true)
            val result = repository.register(
                nombre, apellidoPaterno, apellidoMaterno, username, email, password
            )
            result.fold(
                onSuccess = { user ->
                    _uiState.value = AuthUiState(user = user, isSuccess = true)
                },
                onFailure = { e ->
                    _uiState.value = AuthUiState(errorMessage = e.message)
                }
            )
        }
    }

    fun logout() {
        repository.logout()
        _uiState.value = AuthUiState()
    }

    fun clearError() {
        _uiState.value = _uiState.value.copy(errorMessage = null)
    }
}