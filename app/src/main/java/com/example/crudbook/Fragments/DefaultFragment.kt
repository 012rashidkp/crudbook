package com.example.crudbook.Fragments

import android.os.Bundle
import android.os.Handler
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.crudbook.Activities.MainActivity
import com.example.crudbook.Interfaces.ItemClickInterface
import com.example.crudbook.Adapter.ItemAdapter
import com.example.crudbook.Model.FIleItems
import com.example.crudbook.Network.Status
import com.example.crudbook.R
import com.example.crudbook.Utils.AppUtils.Companion.DisplayMessage
import com.example.crudbook.Utils.NavigationUtils.Companion.CreateScreen
import com.example.crudbook.Utils.NavigationUtils.Companion.DetailScreen
import com.example.crudbook.Utils.NavigationUtils.Companion.UpdateScreen
import com.example.crudbook.databinding.FragmentDefaultBinding
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.DiffUtil.DiffResult
import com.example.crudbook.Model.DeleteRequest


class DefaultFragment : BaseFragment() {
private var binding:FragmentDefaultBinding?=null
lateinit var items:ArrayList<FIleItems>
lateinit var adapter: ItemAdapter
lateinit var holder:RecyclerView.ViewHolder
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        binding= FragmentDefaultBinding.inflate(inflater,container,false)
       InitItemViewModel()
        InitTinyDb()
        binding!!.createbtn.setOnClickListener { CreateScreen(requireActivity()) }

        val token=tinyDb.getString("token")

        MakeApiCall("token ${token}")
        return binding!!.root
    }

    private fun MakeApiCall(token: String) {
     itemViewModel.getfiles(token).observe(requireActivity(),{
         it?.let { resources ->
             when(resources.status){
                 Status.SUCCESS->{
 resources.data?.let { result->
     if (!result.error){
         Loading()
         items=result.fileitems
          adapter= ItemAdapter(requireContext(),items,object :ItemClickInterface{
             override fun ItemClick(id: String,postion:Int,operation:String) {
              val deleteRequest=DeleteRequest(id)
                 when(operation){
                     "detail"->DetailScreen(requireActivity())
                     "update"->UpdateScreen(requireActivity())
                     "delete"->Deletefile("token ${tinyDb.getString("token")}",deleteRequest,adapter,postion)
                 }
               System.out.println("clicked $id")
               System.out.println("operation $operation")
             }

         })
         binding!!.filelist.adapter=adapter
         adapter.notifyDataSetChanged()


     }
     else if (result.error){
         binding!!.usersprogressbar.visibility=View.GONE
         DisplayMessage(binding!!.defaultscreenlayout, result.message, R.drawable.error)

     }
 }
                 }
                 Status.LOADING->{

                 }
                 Status.ERROR->{
                     DisplayMessage(binding!!.defaultscreenlayout, it.message!!, R.drawable.error)

                 }
             }
         }
     })
    }

     fun Deletefile(token: String, deleteRequest: DeleteRequest, adapter: ItemAdapter,position:Int) {
        System.out.println("file__id ${deleteRequest.file_id}")
        itemViewModel.deletefile(token,deleteRequest).observe(requireActivity(),{
            it?.let { resources ->

                when(resources.status){
                    Status.SUCCESS->{
                        resources.data?.let { result->
                            if (!result.error){
                                adapter.removeAt(position)
                                DisplayMessage(binding!!.defaultscreenlayout, result.message, R.drawable.success)

                            }
                            else if (result.error){
                                DisplayMessage(binding!!.defaultscreenlayout, result.message, R.drawable.error)

                            }
                        }
                    }
                    Status.LOADING->{

                    }
                    Status.ERROR->{
                        System.out.println("error ${it.message}")
                        DisplayMessage(binding!!.defaultscreenlayout, it.message!!, R.drawable.error)

                    }
                }
            }
        })
    }


    private fun Loading(){
    Handler().postDelayed({
        binding!!.filelist.visibility=View.VISIBLE
        binding!!.usersprogressbar.visibility=View.GONE
        binding!!.filelist.layoutManager=LinearLayoutManager(requireContext())
    },2500)
}

}