package com.example.crudbook.Model

import com.google.gson.annotations.SerializedName

data class AuthResponse (

    @SerializedName("username")
    val username: String,
    @SerializedName("email")
    val email: String,
    @SerializedName("phone")
    val phone: String,
    @SerializedName("city")
    val city: String,
    @SerializedName("token")
    val token: String,
    @SerializedName("userid")
    val userid: String

) : BaseResponse()