package com.example.contacts.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.contacts.R
import com.example.contacts.databinding.RcItemsBinding
import com.example.contacts.fragments.InfoFragment
import com.example.contacts.model.Contact
import java.util.*


class RvAdapter():RecyclerView.Adapter<RvAdapter.MyViewHolder>() {
    private var onClickUserListener: ((Contact) -> Unit)? = null

    var list = mutableListOf<Contact>()
    set(value) {
        field = value
        notifyDataSetChanged()
    }

    inner class MyViewHolder(private val binding: RcItemsBinding):RecyclerView.ViewHolder(binding.root){
        fun setData(contact: Contact){

            binding.tvItem.text = contact.name
            if (contact.image!=null){
                binding.itemImageView.setImageBitmap(contact.image)
            }else{
                binding.itemImageView.setImageResource(R.drawable.ic_user)
            }

            val item = list[position]

            binding.root.setOnClickListener {
                onClickUserListener?.invoke(item)
            }
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        return MyViewHolder(RcItemsBinding.inflate(LayoutInflater.from(parent.context),parent,false))
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.setData(list[position])
    }

    override fun getItemCount(): Int = list.size



    fun setOnUserClickListener(block: (Contact) -> Unit) {
        onClickUserListener = block
    }


}