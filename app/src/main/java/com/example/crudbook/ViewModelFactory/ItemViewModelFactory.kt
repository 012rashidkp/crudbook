package com.example.crudbook.ViewModelFactory

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.crudbook.Network.ApiHelper
import com.example.crudbook.Repository.AuthRepository
import com.example.crudbook.Repository.ItemRepository
import com.example.crudbook.ViewModel.AuthVieModel
import com.example.crudbook.ViewModel.ItemViewModel

class ItemViewModelFactory(private val apihelper: ApiHelper) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ItemViewModel::class.java)) {
            return ItemViewModel(ItemRepository(apihelper)) as T
        }
        throw IllegalArgumentException("Unknown class name")
    }


}