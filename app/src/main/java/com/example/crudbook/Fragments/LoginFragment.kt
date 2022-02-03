package com.example.crudbook.Fragments

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.crudbook.R
import com.example.crudbook.Utils.NavigationUtils.Companion.RegisterScreen
import com.example.crudbook.databinding.FragmentLoginBinding
import androidx.lifecycle.Observer
import com.example.crudbook.Activities.MainActivity
import com.example.crudbook.Network.Status
import com.example.crudbook.Utils.AppUtils.Companion.DisplayMessage
import com.example.crudbook.Utils.AppUtils.Companion.hideSoftKeyBoard


class LoginFragment : BaseFragment() {
private var binding:FragmentLoginBinding?=null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        binding= FragmentLoginBinding.inflate(inflater, container, false)
        InitTinyDb()
        InitAuthViewModel()
        binding!!.regtxtbtn.setOnClickListener { RegisterScreen(requireActivity()) }


        binding!!.loginbtn.setOnClickListener {
            val email=binding!!.loginemailedit.text.toString().trim()
            val password=binding!!.loginpasswordedit.text.toString().trim()
            if (validated()){
                hideSoftKeyBoard(requireActivity(),binding!!.loginscreenlayout)
                binding!!.loginprogress.visibility=View.VISIBLE
                binding!!.logintext.visibility=View.GONE
                MakeApiCall(email,password)

            }
            else{
                hideSoftKeyBoard(requireActivity(),binding!!.loginscreenlayout)
                DisplayMessage(binding!!.loginscreenlayout,"please complete login details",R.drawable.error)
            }
        }



        return binding!!.root
    }

    private fun MakeApiCall(email: String, password: String) {

        userVieModel.getLogin(email,password).observe(requireActivity(), Observer {
            it?.let { response ->
                when(response.status){
                    Status.SUCCESS->{

                        response.data?.let { datas ->
                        if (!datas.error){
                            Loading()
                            DisplayMessage(binding!!.loginscreenlayout,datas.message,R.drawable.success)
                            tinyDb.putString("token",datas.token)


                        }
                        else if (datas.error){
                            binding!!.loginprogress.visibility=View.GONE
                            binding!!.logintext.visibility=View.VISIBLE
                            DisplayMessage(binding!!.loginscreenlayout,datas.message,R.drawable.error)


                        }



                        }
                    }
                    Status.LOADING->{
                        System.out.println("loading")
                    }
                    Status.ERROR->{
                        System.out.println("error")
                       // hideSoftKeyBoard(requireContext(),binding!!.loginscreenlayout)
                        binding!!.loginprogress.visibility=View.GONE
                        binding!!.logintext.visibility=View.VISIBLE
                        DisplayMessage(binding!!.loginscreenlayout,response.message!!,R.drawable.error)
                    }
                }
            }
        })


    }

    private fun validated():Boolean{
    if (binding!!.loginemailedit.text.toString().equals("")){
        binding!!.loginemailedit.setError("please enter your email")
        return false
    }
    else if (!isValidEmail(binding!!.loginemailedit.text.toString())){
        binding!!.loginemailedit.setError("enter a valid email")
        return false
    }
    else if (binding!!.loginpasswordedit.text.toString().equals("")){
        binding!!.loginpasswordedit.setError("please you password")
        return false
    }
    return true

}
 private fun Loading(){
     Handler().postDelayed({
         val intent=Intent(requireContext(),MainActivity::class.java)
         requireActivity().overridePendingTransition(android.R.anim.slide_in_left,android.R.anim.slide_out_right)
         startActivity(intent)
         requireActivity().finish()
     },2500)
 }
}