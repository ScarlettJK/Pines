package com.example.pines.core.model

import com.google.gson.annotations.SerializedName


data class Pines(
    @SerializedName("id")
    val id: String,

    @SerializedName("alternative_slugs")
    val alternativeSlugs: AlternativeSlugs,

    @SerializedName("urls")
    val urls: Urls
)

data class AlternativeSlugs(
    @SerializedName("en")
    val en: String
)

data class Urls(
    @SerializedName("regular")
    val regular: String
)