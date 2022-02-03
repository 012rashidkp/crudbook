package com.example.crudbook.Fragments

import android.os.Bundle
import android.os.Handler
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import com.example.crudbook.Network.Status
import com.example.crudbook.R
import com.example.crudbook.Utils.AppUtils.Companion.DisplayMessage
import com.example.crudbook.Utils.AppUtils.Companion.hideSoftKeyBoard
import com.example.crudbook.Utils.NavigationUtils.Companion.LoginScreen
import com.example.crudbook.databinding.FragmentRegisterBinding

class RegisterFragment : BaseFragment() {
private var binding:FragmentRegisterBinding?=null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
      binding= FragmentRegisterBinding.inflate(inflater,container,false)
        InitAuthViewModel()
        InitTinyDb()



        binding!!.savebtn.setOnClickListener {
            val username=binding!!.usernameedit.text.toString().trim()
            val email=binding!!.emailedit.text.toString().trim()
            val phone=binding!!.phoneedit.text.toString().trim()
            val city=binding!!.cityedit.text.toString().trim()
            val password=binding!!.passwordedit.text.toString().trim()
            if (validated()){
                System.out.println("params $username $email $phone $city $password")
                hideSoftKeyBoard(requireActivity(), binding!!.registerscreenlayout)
                binding!!.signupprogress.visibility=View.VISIBLE
                binding!!.signuptext.visibility=View.GONE
                MakeApiCall(username,email,phone,city,password)
            }
            else{
                hideSoftKeyBoard(requireActivity(), binding!!.registerscreenlayout)
                DisplayMessage(binding!!.registerscreenlayout, "please complete login details", R.drawable.error)
            }
        }


        return binding!!.root
    }

    private fun MakeApiCall(username: String, email: String, phone: String, city: String, password: String) {
        userVieModel.Registeruser(username,email,phone,city,password).observe(requireActivity(), Observer {
            it?.let { response ->
                when(response.status){
                    Status.SUCCESS->{

                        response.data?.let { datas ->
                            if (!datas.error){
                                Loading()
                                DisplayMessage(binding!!.registerscreenlayout,datas.message,R.drawable.success)
                                tinyDb.putString("token",datas.token)
                               System.out.println("result ${datas.message}")

                            }
                            else if (datas.error){
                                System.out.println("result ${datas.message}")
                                binding!!.signupprogress.visibility=View.GONE
                                binding!!.signuptext.visibility=View.VISIBLE
                                DisplayMessage(binding!!.registerscreenlayout,datas.message,R.drawable.error)


                            }



                        }
                    }
                    Status.LOADING->{
                        System.out.println("loading")
                    }
                    Status.ERROR->{
                        System.out.println("error")
                        // hideSoftKeyBoard(requireContext(),binding!!.loginscreenlayout)
                        binding!!.signupprogress.visibility=View.GONE
                        binding!!.signuptext.visibility=View.VISIBLE
                        DisplayMessage(binding!!.registerscreenlayout,response.message!!,R.drawable.error)
                    }
                }
            }
        })

    }



    private fun validated() :Boolean{
    if (binding!!.usernameedit.text.toString().equals("")){
        binding!!.usernameedit.setError("please enter your username")
        return false
    }
    else if (binding!!.emailedit.text.toString().equals("")){
        binding!!.emailedit.setError("please enter your email")
        return false
    }
    else if (!isValidEmail(binding!!.emailedit.text.toString())){
        binding!!.emailedit.setError("please enter valid email")
        return false
    }
    else if (binding!!.phoneedit.text.toString().equals("")){
        binding!!.phoneedit.setError("please enter your phone number")
        return false
    }
    else if (!(binding!!.phoneedit.text.toString().length>=10)){
        binding!!.phoneedit.setError("please valid phone number")
        return false
    }
    else if (binding!!.cityedit.text.toString().equals("")){
        binding!!.cityedit.setError("please enter your city")
        return false
    }
    else if (binding!!.passwordedit.text.toString().equals("")){
        binding!!.passwordedit.setError("please enter your password")
        return false
    }
    else if (!(binding!!.passwordedit.text.toString().length>=6)){
        binding!!.passwordedit.setError("password length must 6")
        return false
    }
    return true

  }
    private fun Loading() {
   Handler().postDelayed({
       LoginScreen(requireActivity())
   },2500)
    }
}