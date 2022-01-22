package com.example.crudbook.Model

import com.google.gson.annotations.SerializedName

data class MyFile(
    @SerializedName("count")
    val count:String,
    @SerializedName("files")
    val fileitems:ArrayList<FIleItems>
):BaseResponse()


