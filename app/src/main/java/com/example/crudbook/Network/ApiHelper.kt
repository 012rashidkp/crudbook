package com.example.crudbook.Network

import com.example.crudbook.Interfaces.ApiInterface
import com.example.crudbook.Model.DeleteRequest

import com.example.crudbook.Network.Apis.Companion.BaseUrl
import okhttp3.MultipartBody
import okhttp3.OkHttpClient
import okhttp3.RequestBody
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class ApiHelper(val apiInterface: ApiInterface) {
    suspend fun Resgisteruser(username:String, email: String, phone:String, city:String, password: String)=apiInterface.RegisterUser(username,email,phone,city,password)

    suspend fun Loginuser(email:String,password:String)=apiInterface.LoginUser(email,password)

    suspend fun Uploadfile(token:String,filename:RequestBody,desc:RequestBody,myfile: MultipartBody.Part)=apiInterface.UploadFile(token,filename,desc,myfile)

    suspend fun getFiles(token:String)=apiInterface.getfiles(token)

    suspend fun deletefiles(token: String,deleteRequest: DeleteRequest)=apiInterface.deletefile(token,deleteRequest)
}