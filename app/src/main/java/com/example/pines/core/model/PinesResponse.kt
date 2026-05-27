package com.example.pines.core.model

import com.google.gson.annotations.SerializedName


data class Pines(
    @SerializedName("id")
    val id: String,

    @SerializedName("alt_description")
    val altDescription: String?,

    @SerializedName("urls")
    val urls: Urls
)

data class Urls(
    @SerializedName("regular")
    val regular: String
)