package com.example.crudbook.Adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import coil.api.load
import com.example.crudbook.Fragments.DefaultFragment
import com.example.crudbook.Interfaces.ItemClickInterface
import com.example.crudbook.Model.FIleItems
import com.example.crudbook.databinding.ItemListLayoutBinding

class ItemAdapter(private val context: Context, private val items:ArrayList<FIleItems>, private val listener:ItemClickInterface):RecyclerView.Adapter<ItemAdapter.MyViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val binding=ItemListLayoutBinding.inflate(LayoutInflater.from(context),parent,false)
        return MyViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val datas=items[position]
        with(holder){
            binding.filename.text=datas.fileName
            binding.filedesc.text=datas.fileDesc
            binding.fileimg.load(datas.myfile)

            itemView.setOnClickListener { listener.ItemClick(datas.file_id,position,"detail") }
            binding.updatebtn.setOnClickListener { listener.ItemClick(datas.file_id,position,"update") }
            binding.deletebtn.setOnClickListener { listener.ItemClick(datas.file_id,position,"delete") }
        }
    }

    override fun getItemCount(): Int {
        return items.size
    }
    fun getFIleAt(position: Int):FIleItems{
        return items.get(position)
    }

    public fun removeAt(position: Int) {
        items.removeAt(position)
        notifyItemRemoved(position)
        notifyItemRangeChanged(position, items.size)
        notifyDataSetChanged()
       // (context as ItemsActivity).showItems()

    }

    class MyViewHolder(val binding:ItemListLayoutBinding):RecyclerView.ViewHolder(binding.root) {

    }

}