package com.siemens.leadx.data.remote.entites

import com.google.gson.annotations.SerializedName

data class UserData(

    @field:SerializedName("access_token")
    val accessToken: String? = null,

    @field:SerializedName("refresh_token")
    val refreshToken: String? = null,

    @field:SerializedName("token_type")
    val tokenType: String? = null,

    @field:SerializedName("user")
    val user: User? = null,

    @field:SerializedName("expires_in")
    val expiresIn: Int? = null,
)

data class User(

    @field:SerializedName("name")
    val name: String,

    @field:SerializedName("id")
    val id: String,

    @field:SerializedName("email")
    val email: String,
) {
    fun getNameAsLetters(): String {
        val names = name.trim().split(" ")
        return when {
            names.size > 1 -> return "${names[0][0].uppercase()}${names[1][0].uppercase()}"
            names.isNotEmpty() -> names[0][0].uppercase()
            else -> ""
        }
    }
}
