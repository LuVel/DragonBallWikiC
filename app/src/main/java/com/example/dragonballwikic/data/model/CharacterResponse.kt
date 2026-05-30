package com.example.dragonballwikic.data.model

import com.google.gson.annotations.SerializedName

data class CharacterResponse(
    @SerializedName("items")
    val items: List<Character>,

    @SerializedName("meta")
    val meta: Meta?
)

data class Meta(
    @SerializedName("totalItems")
    val totalItems: Int,

    @SerializedName("itemCount")
    val itemCount: Int,

    @SerializedName("itemsPerPage")
    val itemsPerPage: Int,

    @SerializedName("totalPages")
    val totalPages: Int,

    @SerializedName("currentPage")
    val currentPage: Int
)