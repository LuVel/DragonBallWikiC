package com.example.dragonballwikic.data.model

import com.google.gson.annotations.SerializedName

data class Character(
    @SerializedName("id")
    val id: Int,

    @SerializedName("name")
    val name: String,

    @SerializedName("ki")
    val ki: String,

    @SerializedName("maxKi")
    val maxKi: String,

    @SerializedName("race")
    val race: String,

    @SerializedName("gender")
    val gender: String,

    @SerializedName("description")
    val description: String,

    @SerializedName("image")
    val image: String,

    @SerializedName("affiliation")
    val affiliation: String,

    @SerializedName("originPlanet")
    val originPlanet: Planet?,

    @SerializedName("transformations")
    val transformations: List<Transformation>?
)