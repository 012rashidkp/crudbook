package com.example.crudbook.Repository

import com.example.crudbook.Network.ApiHelper

class AuthRepository(val apiHelper: ApiHelper) {
    suspend fun Resgisteruser(username:String, email: String, phone:String, city:String, password: String)=apiHelper.Resgisteruser(username,email,phone,city,password)

    suspend fun LoginUser(email:String,password:String)=apiHelper.Loginuser(email,password)
}