package com.example.myfamilyproject

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.myfamily.ContactModel
import com.example.myfamilyproject.databinding.ItemInviteBinding

class InviteAdapter(private val listContacts: List<ContactModel>) :
    RecyclerView.Adapter<InviteAdapter.ViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val item = ItemInviteBinding.inflate(inflater, parent, false)
        return ViewHolder(item)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        val item = listContacts[position]
        holder.name.text = item.name

    }

    override fun getItemCount(): Int {
        return listContacts.size
    }

    class ViewHolder(private val item: ItemInviteBinding) : RecyclerView.ViewHolder(item.root) {
        val name = item.name

    }
}