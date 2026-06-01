package com.example.dragonballwikic.data.remote

import com.example.dragonballwikic.data.model.Character
import com.example.dragonballwikic.data.model.CharacterResponse
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface DragonBallApiService {

    // Buscar personaje por nombre
    @GET("characters")
    suspend fun searchCharacterByName(
        @Query("name") name: String
    ):List<Character>

    // Obtener personaje por ID
    @GET("characters/{id}")
    suspend fun getCharacterById(
        @Path("id") id: Int
    ): Character

    // Obtener lista paginada (fallback)
    @GET("characters")
    suspend fun getAllCharacters(
        @Query("page") page: Int = 1,
        @Query("limit") limit: Int = 10
    ): CharacterResponse
}