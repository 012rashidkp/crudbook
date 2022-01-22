package com.example.crudbook.ViewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import com.example.crudbook.Model.DeleteRequest
import com.example.crudbook.Network.Resources
import com.example.crudbook.Repository.ItemRepository
import kotlinx.coroutines.Dispatchers
import okhttp3.MultipartBody
import okhttp3.RequestBody

class ItemViewModel(private val repository: ItemRepository):ViewModel() {


    fun Uploadfile(token:String, filename: RequestBody, desc: RequestBody, myfile: MultipartBody.Part) = liveData(Dispatchers.IO) {
        emit(Resources.loading(data = null))
        try {
            emit(Resources.success(data = repository.Uploadfile(token,filename,desc,myfile)))
            System.out.println("got result")
        } catch (exception: Exception) {
            emit(Resources.error(data = null, message = exception.message ?: "Error Occurred!"))
        }
    }


    fun getfiles(token:String) = liveData(Dispatchers.IO) {
        emit(Resources.loading(data = null))
        try {
            emit(Resources.success(data = repository.getfiles(token)))
            System.out.println("got result")
        } catch (exception: Exception) {
            emit(Resources.error(data = null, message = exception.message ?: "Error Occurred!"))
        }
    }
    fun deletefile(token:String,deleteRequest: DeleteRequest) = liveData(Dispatchers.IO) {
        emit(Resources.loading(data = null))
        try {
            emit(Resources.success(data = repository.deletefiles(token,deleteRequest)))
            System.out.println("got result")
        } catch (exception: Exception) {
            emit(Resources.error(data = null, message = exception.message ?: "Error Occurred!"))
        }
    }
}