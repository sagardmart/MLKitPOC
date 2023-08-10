package com.mlkitpoc.main

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.EditText
import android.widget.ImageButton
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.RecyclerView
import com.mlkitpoc.R


class MainAdapter(val userList: ArrayList<String>) : RecyclerView.Adapter<MainAdapter.ViewHolder>() {

    lateinit var parentholder: ViewGroup

    class ViewHolder(itemView : View) : RecyclerView.ViewHolder(itemView) {
        val itemName = itemView.findViewById<CheckBox>(R.id.checkbox)
        val deleteButton = itemView.findViewById<ImageButton>(R.id.delete_item)
        val editButton = itemView.findViewById<ImageButton>(R.id.edit_item_button)
    }

    override fun onCreateViewHolder(parent: ViewGroup, p1: Int): ViewHolder {
        val v = LayoutInflater.from(parent?.context).inflate(R.layout.checklist_item, parent, false)
        parentholder = parent
        return ViewHolder(v)
    }

    override fun getItemCount(): Int {
        return userList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        // displays item
        holder.itemName?.setText(userList[position])

        // deletes item
        holder.deleteButton?.setOnClickListener {
            userList.removeAt(position)
            notifyItemRemoved(position)
            notifyItemRangeChanged(position, userList.size - position)
        }

        // edits item
       holder.editButton?.setOnClickListener {
            val builder = AlertDialog.Builder(it.context)
            val inflater = LayoutInflater.from(parentholder?.context)
            builder.setTitle("Edit Item")
            builder.setNeutralButton("Cancel") { dialog, i -> }
            val dialogLayout = inflater.inflate(R.layout.add_item_popup, null)
            val editText  = dialogLayout.findViewById<EditText>(R.id.editText)
            editText.setText(userList[position])
            builder.setView(dialogLayout)
            builder.setPositiveButton("OK") { dialog, i ->
                userList.removeAt(position)
                userList.add(position, editText.text.toString())
                notifyItemChanged(position)
            }
            builder.show()
        }

    }
}