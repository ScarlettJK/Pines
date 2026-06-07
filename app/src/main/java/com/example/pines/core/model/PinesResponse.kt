package com.example.pines.core.model

//import android.os.Parcelable
import com.google.gson.annotations.SerializedName
//import kotlinx.parcelize.Parcelize
import java.io.Serializable

//@Parcelize
data class Pines(

    @SerializedName("id")
    val id: String = "",

    @SerializedName("likes")
    val likes: Int = 0,

    @SerializedName("liked_by_user")
    val likedByUser: Boolean = false,

    @SerializedName("alt_description")
    val altDescription: String? = null,

    @SerializedName("alternative_slugs")
    val alternativeSlugs: AlternativeSlugs = AlternativeSlugs(),

    @SerializedName("urls")
    val urls: Urls = Urls(),

    @SerializedName("user")
    val user: User = User()

) : Serializable /*Parcelable*/

//@Parcelize
data class AlternativeSlugs(

    @SerializedName("en")
    val en: String = ""

) : Serializable /*Parcelable*/

//@Parcelize
data class Urls(

    @SerializedName("regular")
    val regular: String = ""

) : Serializable /*Parcelable*/

//@Parcelize
data class User(

    @SerializedName("username")
    val username: String = "",

    @SerializedName("name")
    val name: String = "",

    @SerializedName("location")
    val location: String? = null

) : Serializable /*Parcelable*/