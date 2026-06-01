package com.example.dragonballwikic.data.repository

import com.example.dragonballwikic.data.model.Character
import com.example.dragonballwikic.data.model.CharacterResponse
import com.example.dragonballwikic.data.remote.RetrofitInstance

class CharacterRepository {

    private val api = RetrofitInstance.api

    // Buscar personaje por nombre
    suspend fun searchCharacterByName(name: String): Result<List<Character>> {
        return try {
            val response = api.searchCharacterByName(name)
            Result.success(response)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    // Obtener personaje por ID
    suspend fun getCharacterById(id: Int): Result<Character> {
        return try {
            val character = api.getCharacterById(id)
            Result.success(character)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    // Obtener lista completa (fallback)
    suspend fun getAllCharacters(page: Int = 1): Result<List<Character>> {
        return try {
            val response = api.getAllCharacters(page = page)
            Result.success(response.items)  // ← agrega .items
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}