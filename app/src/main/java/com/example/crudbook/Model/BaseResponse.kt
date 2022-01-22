package com.example.crudbook.Model

import com.google.gson.annotations.SerializedName

open class BaseResponse{
    @SerializedName("error")
    val error: Boolean=false
    @SerializedName("message")
    val message: String=""
}
