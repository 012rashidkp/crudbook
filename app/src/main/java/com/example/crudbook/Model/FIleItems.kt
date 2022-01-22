package com.example.crudbook.Model

import com.google.gson.annotations.SerializedName

data class FIleItems(
    @SerializedName("file_id")
    val file_id: String,
    @SerializedName("fileName")
    val fileName: String,
    @SerializedName("fileDesc")
    val fileDesc: String,
    @SerializedName("myfile")
    val myfile: String

)
