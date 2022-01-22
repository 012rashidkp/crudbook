package com.example.crudbook.Repository


import com.example.crudbook.Model.DeleteRequest
import com.example.crudbook.Network.ApiHelper
import okhttp3.MultipartBody
import okhttp3.RequestBody

class ItemRepository(private val apiHelper: ApiHelper) {
    suspend fun Uploadfile(token:String, filename: RequestBody, desc: RequestBody, myfile: MultipartBody.Part)=apiHelper.Uploadfile(token,filename,desc,myfile)
    suspend fun getfiles(token:String)=apiHelper.getFiles(token)
    suspend fun deletefiles(token: String,deleteRequest: DeleteRequest)=apiHelper.deletefiles(token,deleteRequest)

}