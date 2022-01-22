package com.example.crudbook.ViewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import com.example.crudbook.Network.Resources
import com.example.crudbook.Repository.AuthRepository
import kotlinx.coroutines.Dispatchers

class AuthVieModel(val userRepository: AuthRepository):ViewModel() {


    fun getLogin(email:String,password:String) = liveData(Dispatchers.IO) {
        emit(Resources.loading(data = null))
        try {
            emit(Resources.success(data = userRepository.LoginUser(email,password)))
            System.out.println("got result")
        } catch (exception: Exception) {
            emit(Resources.error(data = null, message = exception.message ?: "Error Occurred!"))
        }
    }


    fun Registeruser(username:String, email: String, phone:String, city:String, password: String) = liveData(Dispatchers.IO) {
        emit(Resources.loading(data = null))
        try {
            emit(Resources.success(data = userRepository.Resgisteruser(username,email,phone,city,password)))
            System.out.println("got result")
        } catch (exception: Exception) {
            emit(Resources.error(data = null, message = exception.message ?: "Error Occurred!"))
        }
    }
}