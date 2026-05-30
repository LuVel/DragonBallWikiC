package com.example.dragonballwikic.data.model

import com.google.gson.annotations.SerializedName

data class Transformation(
    @SerializedName("id")
    val id: Int,

    @SerializedName("name")
    val name: String,

    @SerializedName("image")
    val image: String,

    @SerializedName("ki")
    val ki: String
)