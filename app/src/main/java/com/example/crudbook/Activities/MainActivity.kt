package com.example.crudbook.Activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.crudbook.R
import com.example.crudbook.Utils.NavigationUtils.Companion.CallDefaultFragment


class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        CallDefaultFragment(this)
    }
}