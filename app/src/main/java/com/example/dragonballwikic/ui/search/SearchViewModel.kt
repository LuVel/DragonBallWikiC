package com.example.dragonballwikic.ui.search

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.dragonballwikic.data.model.Character
import com.example.dragonballwikic.data.repository.CharacterRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

data class SearchUiState(
    val isLoading: Boolean = false,
    val character: Character? = null,
    val errorMessage: String? = null,
    val hasSearched: Boolean = false
)

class SearchViewModel : ViewModel() {

    private val repository = CharacterRepository()

    private val _uiState = MutableStateFlow(SearchUiState())
    val uiState: StateFlow<SearchUiState> = _uiState

    fun searchCharacter(name: String) {
        if (name.isBlank()) return
        val normalizedName = name.trim()
            .replaceFirstChar { it.uppercase() }

        val lastCharacter = _uiState.value.character

        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(
                isLoading = true,
                errorMessage = null
            )

            try {
                val result = repository.searchCharacterByName(normalizedName)
                if (result.isSuccess) {
                    val characters = result.getOrNull()
                    if (characters != null && characters.isNotEmpty()) {
                        _uiState.value = _uiState.value.copy(
                            isLoading = false,
                            character = characters[0],
                            hasSearched = true,
                            errorMessage = null
                        )
                    } else {
                        _uiState.value = _uiState.value.copy(
                            isLoading = false,
                            character = lastCharacter,
                            hasSearched = true,
                            errorMessage = "Personaje no encontrado"
                        )
                    }
                } else {
                    val errorMsg = result.exceptionOrNull()?.message ?: "desconocido"
                    _uiState.value = _uiState.value.copy(
                        isLoading = false,
                        character = lastCharacter,
                        hasSearched = true,
                        errorMessage = "Error: $errorMsg" //Necesito restaurar está etiqueta
                    )
                }
            } catch (e: Exception) {
                _uiState.value = _uiState.value.copy(
                    isLoading = false,
                    character = lastCharacter,
                    hasSearched = true,
                    errorMessage = "Error: ${e.message}"
                )
            }
        }
    }

    fun clearError() {
        _uiState.value = _uiState.value.copy(errorMessage = null)
    }
}