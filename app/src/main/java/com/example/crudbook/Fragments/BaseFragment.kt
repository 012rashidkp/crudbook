package com.example.crudbook.Fragments

import android.os.Bundle
import android.util.Patterns
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import com.example.crudbook.Network.ApiClient
import com.example.crudbook.Network.ApiHelper
import com.example.crudbook.Utils.TinyDb
import com.example.crudbook.ViewModel.AuthVieModel
import com.example.crudbook.ViewModel.ItemViewModel
import com.example.crudbook.ViewModelFactory.AuthViewModelFactory
import com.example.crudbook.ViewModelFactory.ItemViewModelFactory
import java.util.regex.Pattern


open class BaseFragment : Fragment() {

    lateinit var tinyDb: TinyDb
    lateinit var userVieModel: AuthVieModel
    lateinit var itemViewModel: ItemViewModel
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return view
    }
fun InitTinyDb(){
    tinyDb= TinyDb(requireContext())
}
fun InitViewModel(){
    userVieModel= ViewModelProvider(
        this,
        AuthViewModelFactory(ApiHelper(ApiClient.invoke()))
    )[AuthVieModel::class.java]
}
fun InitItemViewModel(){
    itemViewModel= ViewModelProvider(
        this,
        ItemViewModelFactory(ApiHelper(ApiClient.invoke()))
    )[ItemViewModel::class.java]
}
     fun isValidEmail(email: String): Boolean {
        val pattern: Pattern = Patterns.EMAIL_ADDRESS
        return pattern.matcher(email).matches()
    }
}