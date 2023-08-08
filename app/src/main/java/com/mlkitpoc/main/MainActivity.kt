package com.mlkitpoc.main

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.mlkitpoc.R
import com.mlkitpoc.list.ListActivity

class MainActivity : AppCompatActivity() {
    @SuppressLint("MissingInflatedId", "WrongViewCast")
    var list = ArrayList<String>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // setting up recycler view
        val groceryList = findViewById<RecyclerView>(R.id.recycler_main_list)
        groceryList.layoutManager = LinearLayoutManager(this, RecyclerView.VERTICAL, false)
        groceryList.adapter = MainAdapter(list)

        // add button which takes user input
        val addButton = findViewById<Button>(R.id.add_item_button)
        addButton.setOnClickListener {
            val builder = AlertDialog.Builder(this)
            val inflater = layoutInflater
            builder.setTitle("Add Item")
            builder.setNeutralButton("Cancel") { dialog, i -> }
            val dialogLayout = inflater.inflate(R.layout.add_item_popup, null)
            val editText = dialogLayout.findViewById<EditText>(R.id.editText)
            builder.setView(dialogLayout)
            builder.setPositiveButton("OK") { dialog, i ->
                list.add(editText.text.toString())
                groceryList.adapter = MainAdapter(list)
            }
            builder.show()
        }

        // goes to scan activity
        val scanButton = findViewById<Button>(R.id.scan_button)
        scanButton.setOnClickListener {
//            val launchScan = Intent(this, ScanActivity::class.java)
//            startActivity(launchScan)
        }

        // goes to results (list activity)
        val doneButton = findViewById<Button>(R.id.done_button)
        doneButton.setOnClickListener {
            val launchList = Intent(this, ListActivity::class.java)
            launchList.putStringArrayListExtra("itemList", list)
            startActivity(launchList)
        }
    }
}

