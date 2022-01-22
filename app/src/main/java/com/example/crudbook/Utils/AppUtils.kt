package com.example.crudbook.Utils

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Color
import android.view.View
import android.view.inputmethod.InputMethodManager
import de.mateware.snacky.Snacky

class AppUtils {

        companion object{

            @SuppressLint("ServiceCast")
            fun hideSoftKeyBoard(context: Context, view: View) {
                try {
                    val imm = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
                    imm?.hideSoftInputFromWindow(view.windowToken, InputMethodManager.HIDE_NOT_ALWAYS)
                } catch (e: Exception) {
                    // TODO: handle exception
                    e.printStackTrace()
                }

            }
            fun DisplayMessage(view: View, message: String, icon:Int){
                Snacky.builder()
                    .setView(view)
                    .setBackgroundColor(Color.parseColor("#FF4500"))
                    .setIcon(icon)
                    .setText(message)
                    .setActionTextTypefaceStyle(Int.SIZE_BITS)
                    .setActionTextColor(Color.parseColor("#ffffff"))
                    .setDuration(Snacky.LENGTH_LONG)
                    .error()
                    .show()
            }

        }
}