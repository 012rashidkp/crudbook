package com.example.crudbook.Utils

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.ListFragment
import com.example.crudbook.Fragments.*
import com.example.crudbook.R

class NavigationUtils :Fragment() {

    companion object{

        fun LoginScreen(activity: FragmentActivity){
            activity.supportFragmentManager.
            beginTransaction().setCustomAnimations(android.R.anim.slide_in_left,android.R.anim.slide_out_right)
                .addToBackStack(null).replace(R.id.fragment_container, LoginFragment()).commit()
        }
        fun RegisterScreen(activity: FragmentActivity){
            activity.supportFragmentManager.
            beginTransaction().setCustomAnimations(android.R.anim.slide_in_left,android.R.anim.slide_out_right)
                .addToBackStack(null).replace(R.id.fragment_container, RegisterFragment()).commit()
        }

        fun CallDefaultFragment(activity: FragmentActivity){
            activity.supportFragmentManager.
            beginTransaction().setCustomAnimations(android.R.anim.slide_in_left,android.R.anim.slide_out_right)
                .replace(R.id.fragment_container, DefaultFragment()).commit()
        }
        fun CreateScreen(activity: FragmentActivity){
            activity.supportFragmentManager.
            beginTransaction().setCustomAnimations(android.R.anim.slide_in_left,android.R.anim.slide_out_right)
                .addToBackStack(null).replace(R.id.fragment_container,CreateFragment()).commit()
        }
        fun UpdateScreen(activity: FragmentActivity){
            activity.supportFragmentManager.
            beginTransaction().setCustomAnimations(android.R.anim.slide_in_left,android.R.anim.slide_out_right)
                .addToBackStack(null).replace(R.id.fragment_container,UpdateFragment()).commit()
        }

        fun DetailScreen(activity: FragmentActivity){
            activity.supportFragmentManager.
            beginTransaction().setCustomAnimations(android.R.anim.slide_in_left,android.R.anim.slide_out_right)
                .addToBackStack(null).replace(R.id.fragment_container,DetailFragment()).commit()
        }
    }
}