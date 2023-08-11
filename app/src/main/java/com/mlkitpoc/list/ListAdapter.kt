package com.mlkitpoc.list

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.mlkitpoc.R
import com.mlkitpoc.integration.DetailsFragment
import com.mlkitpoc.integration.ml.model.Items

class ListAdapter(private val itemList: List<Items>,
private val onItemClick: (String)-> Unit) :
    RecyclerView.Adapter<ListAdapter.ItemViewHolder>() {

    class ItemViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val itemNameTextView: TextView = itemView.findViewById(R.id.itemname)
        val checkBox: CheckBox = itemView.findViewById(R.id.checkBox)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.listcard_f, parent, false)
        return ItemViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        val item = itemList[position]
        holder.itemNameTextView.text = item.text
        holder.checkBox.isChecked = item.isChecked
        Log.d("TAG", "onBindViewHolder: pos before " + position)
        Log.d("TAG", "onBindViewHolder: is checked " + item.isChecked)
        holder.itemNameTextView.setOnClickListener {
            onItemClick(item.text)
        }

        holder.checkBox.setOnCheckedChangeListener { compoundButton, b ->
            Log.d("TAG", "onBindViewHolder: pos on click" + position)
            itemList[position].isChecked = b
        }

    }

    override fun getItemCount(): Int {
        return itemList.size
    }
}
