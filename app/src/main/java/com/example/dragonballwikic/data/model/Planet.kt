package com.example.dragonballwikic.data.model

import com.google.gson.annotations.SerializedName

data class Planet(
    @SerializedName("id")
    val id: Int,

    @SerializedName("name")
    val name: String,

    @SerializedName("image")
    val image: String,

    @SerializedName("description")
    val description: String
)