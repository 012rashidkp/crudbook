package com.example.crudbook.Activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.crudbook.Utils.ConnectivityLiveData

open class BaseActivity : AppCompatActivity() {
    private lateinit var connectivityLiveData: ConnectivityLiveData
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)



    }
    fun InitNetworkConnection(){
        connectivityLiveData= ConnectivityLiveData(application)
        connectivityLiveData.observe(this, androidx.lifecycle.Observer { isAvailable->
            when(isAvailable)
            {
                true->
                    Toast.makeText(applicationContext,"connection available", Toast.LENGTH_SHORT).show()
                false->Toast.makeText(applicationContext,"no network connection",Toast.LENGTH_SHORT).show()
            }
        })
    }
}