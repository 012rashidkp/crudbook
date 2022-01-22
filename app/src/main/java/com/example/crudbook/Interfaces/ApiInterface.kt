package com.example.crudbook.Interfaces

import com.example.crudbook.Model.AuthResponse
import com.example.crudbook.Model.BaseResponse
import com.example.crudbook.Model.DeleteRequest

import com.example.crudbook.Model.MyFile
import com.example.crudbook.Network.Apis.Companion.Login
import com.example.crudbook.Network.Apis.Companion.Register
import com.example.crudbook.Network.Apis.Companion.Upload
import com.example.crudbook.Network.Apis.Companion.deletefile
import com.example.crudbook.Network.Apis.Companion.retrievefile
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.http.*

interface ApiInterface {
   @FormUrlEncoded
   @POST(Login)
    suspend fun LoginUser(@Field("email") email:String,@Field("password") password:String):AuthResponse

   @FormUrlEncoded
   @POST(Register)
   suspend fun RegisterUser(
       @Field("username")username:String,
       @Field("email")email: String,
       @Field("phone") phone:String,
       @Field("city")city:String,
       @Field("password")password:String ):AuthResponse

   @Multipart
   @POST(Upload)
   suspend fun UploadFile(
       @Header("Authorization") token: String,
       @Part("fileName") filename:RequestBody,
       @Part("fileDesc") filedesc:RequestBody,
       @Part myfile: MultipartBody.Part):BaseResponse

   @GET(retrievefile)
   suspend fun getfiles(@Header("Authorization") token: String):MyFile


   @POST(deletefile)
   suspend fun deletefile(@Header("Authorization") token: String, @Body deleteRequest: DeleteRequest):BaseResponse
}