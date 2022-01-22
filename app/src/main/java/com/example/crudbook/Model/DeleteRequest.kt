package com.example.crudbook.Model

import com.google.gson.annotations.SerializedName

data class DeleteRequest(
    @SerializedName("file_id")
    val file_id:String
)
