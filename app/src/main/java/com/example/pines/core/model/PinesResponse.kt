package com.example.pines.core.model

//import android.os.Parcelable
import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize


@Parcelize
data class Pines(

    @SerializedName("id")
    val id: String,

    @SerializedName("likes")
    val likes: Int,

    @SerializedName("liked_by_user")
    val likedByUser: Boolean,

    @SerializedName("alt_description")
    val altDescription: String?,

    @SerializedName("alternative_slugs")
    val alternativeSlugs: AlternativeSlugs,

    @SerializedName("urls")
    val urls: Urls,

    @SerializedName("user")
    val user: User

) : Parcelable

@Parcelize
data class AlternativeSlugs(

    @SerializedName("en")
    val en: String

) : Parcelable

@Parcelize
data class Urls(

    @SerializedName("regular")
    val regular: String

) : Parcelable

@Parcelize
data class User(

    @SerializedName("username")
    val username: String,

    @SerializedName("name")
    val name: String,

    @SerializedName("location")
    val location: String?

) : Parcelable