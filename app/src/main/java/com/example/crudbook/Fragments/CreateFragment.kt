package com.example.crudbook.Fragments

import android.Manifest.permission.READ_EXTERNAL_STORAGE
import android.R.attr
import android.app.Activity.RESULT_OK
import android.content.ContentResolver
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.crudbook.R
import com.example.crudbook.Utils.AppUtils
import com.example.crudbook.Utils.AppUtils.Companion.DisplayMessage
import com.example.crudbook.databinding.FragmentCreateBinding
import android.os.FileUtils
import android.provider.OpenableColumns
import okhttp3.MediaType
import okhttp3.MultipartBody

import okhttp3.RequestBody
import java.io.*
import android.R.attr.data
import android.content.Context
import android.content.pm.PackageManager
import android.database.Cursor
import android.opengl.GLUtils.getType
import android.os.Handler
import com.example.crudbook.Network.Status
import com.example.crudbook.Utils.AppUtils.Companion.hideSoftKeyBoard
import com.example.crudbook.Utils.NavigationUtils
import com.example.crudbook.Utils.NavigationUtils.Companion.CallDefaultFragment
import com.example.crudbook.Utils.NavigationUtils.Companion.CreateScreen
import org.apache.commons.io.FilenameUtils
import android.os.Build
import android.util.Log
import java.lang.Exception
import android.os.Environment
import android.provider.DocumentsContract

import android.text.TextUtils
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.content.ContextCompat.checkSelfPermission
import java.util.Collections.copy
import java.util.jar.Manifest


class CreateFragment : BaseFragment() {
    private var binding: FragmentCreateBinding? = null
    private val IMAGE_PICK_CODE = 1000
    private val PERMISSION_CODE = 1001
    private var imageUri: Uri? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentCreateBinding.inflate(inflater, container, false)
        InitTinyDb()
        InitItemViewModel()

        binding!!.pickbtn.setOnClickListener {
            //check permission at runtime
            val checkSelfPermission = ContextCompat.checkSelfPermission(requireContext(),
                android.Manifest.permission.WRITE_EXTERNAL_STORAGE)
            if (checkSelfPermission != PackageManager.PERMISSION_GRANTED){
                //Requests permissions to be granted to this application at runtime
                ActivityCompat.requestPermissions(requireActivity(),
                    arrayOf(android.Manifest.permission.WRITE_EXTERNAL_STORAGE), 1)
            }
            else{
                pickImageFromGallery()
            }
        }




        binding!!.uploadbtn.setOnClickListener {
            val token = tinyDb.getString("token")
            val FName = binding!!.nameedit.text.toString().trim()
            val desc = binding!!.descedit.text.toString().trim()
            val file = getFileFromUri(imageUri!!)
            val filename: RequestBody = RequestBody.create(MultipartBody.FORM, FName)
            val filedesc: RequestBody = RequestBody.create(MultipartBody.FORM, desc)

            val requestFile = RequestBody.create(MediaType.parse(context?.contentResolver?.getType(imageUri!!)), file)

            val myfile = MultipartBody.Part.createFormData("myfile", file!!.name, requestFile)

            if (validated()) {
                binding!!.uploadprogress.visibility = View.VISIBLE
                binding!!.uploadtext.visibility = View.GONE
                MakeApiCall("token ${token}", filename, filedesc, myfile)
            } else {
                DisplayMessage(binding!!.uploadscreenlayout, "please complete upload form", R.drawable.error)

            }

        }

        return binding!!.root
    }




    private fun validated(): Boolean {
        if (imageUri == null) {
            DisplayMessage(binding!!.uploadscreenlayout, "please select an image", R.drawable.error)
            return false
        } else if (binding!!.nameedit.text.toString().equals("")) {
            binding!!.nameedit.setError("please enter file name")
            return false
        } else if (binding!!.descedit.text.toString().equals("")) {
            binding!!.descedit.setError("please enter file description")
            return false
        }
        return true
    }

    private fun MakeApiCall(
        token: String,
        filename: RequestBody,
        description: RequestBody,
        myfile: MultipartBody.Part
    ) {

        itemViewModel.Uploadfile(token, filename, description, myfile).observe(requireActivity(), {
            it?.let { resources ->
                when (resources.status) {
                    Status.SUCCESS -> {
                        resources.data?.let { result ->
                            if (!result.error) {

                                Loading()
                                DisplayMessage(binding!!.uploadscreenlayout,result.message,R.drawable.success)

                            } else if (result.error) {
                                binding!!.uploadprogress.visibility = View.GONE
                                binding!!.uploadtext.visibility = View.VISIBLE
                                DisplayMessage(binding!!.uploadscreenlayout,result.message, R.drawable.success)

                            }
                        }
                    }
                    Status.LOADING -> {
                        System.out.println("loading")
                    }
                    Status.ERROR -> {
                        binding!!.uploadprogress.visibility = View.GONE
                        binding!!.uploadtext.visibility = View.VISIBLE
                        DisplayMessage(binding!!.uploadscreenlayout,it.message!!,R.drawable.error)
                        System.out.println("message ${it.message}")

                    }
                }
            }
        })


    }

    private fun Loading() {
        Handler().postDelayed({
            binding!!.uploadprogress.visibility = View.GONE
            binding!!.uploadtext.visibility = View.VISIBLE
            CallDefaultFragment(requireActivity())
        }, 2500)
    }

    fun getFileFromUri(uri: Uri): File? {
        if (uri.path == null) {
            return null
        }
        var realPath = String()
        val databaseUri: Uri
        val selection: String?
        val selectionArgs: Array<String>?
        if (uri.path!!.contains("/document/image:")) {
            databaseUri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI
            selection = "_id=?"
            selectionArgs = arrayOf(DocumentsContract.getDocumentId(uri).split(":")[1])
        } else {
            databaseUri = uri
            selection = null
            selectionArgs = null
        }
        try {
            val column = "_data"
            val projection = arrayOf(column)
            val cursor = context?.contentResolver?.query(
                databaseUri,
                projection,
                selection,
                selectionArgs,
                null
            )
            cursor?.let {
                if (it.moveToFirst()) {
                    val columnIndex = cursor.getColumnIndexOrThrow(column)
                    realPath = cursor.getString(columnIndex)
                }
                cursor.close()
            }
        } catch (e: Exception) {
            Log.i("GetFileUri Exception:", e.message ?: "")
        }
        val path = if (realPath.isNotEmpty()) realPath else {
            when {
                uri.path!!.contains("/document/raw:") -> uri.path!!.replace(
                    "/document/raw:",
                    ""
                )
                uri.path!!.contains("/document/primary:") -> uri.path!!.replace(
                    "/document/primary:",
                    "/storage/emulated/0/"
                )
                else -> return null
            }
        }
        return File(path)
    }

    private fun pickImageFromGallery() {
        val intent = Intent(Intent.ACTION_PICK)
        intent.type = "image/*"
        startActivityForResult(intent, IMAGE_PICK_CODE)

    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        when(requestCode){
            PERMISSION_CODE -> {
                if (grantResults.size >0 && grantResults[0] ==
                    PackageManager.PERMISSION_GRANTED){
                    //permission from popup granted
                    pickImageFromGallery()
                }
                else{
                    //permission from popup denied
                    Toast.makeText(requireContext(), "Permission denied", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == RESULT_OK && requestCode == IMAGE_PICK_CODE) {
            imageUri = data?.data
            binding!!.pickimg.setImageURI(imageUri)
        }
    }
}