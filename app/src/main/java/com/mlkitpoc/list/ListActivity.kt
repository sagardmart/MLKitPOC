package com.mlkitpoc.list

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.mlkitpoc.R

class ListActivity : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var itemAdapter: ListAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.list_f)

        val itemList = intent.getStringArrayListExtra("itemList") ?: emptyList()

        recyclerView = findViewById(R.id.recyclerview)
        itemAdapter = ListAdapter(itemList)

        recyclerView.apply {
            layoutManager = LinearLayoutManager(this@ListActivity)
            adapter = itemAdapter
        }
    }
}
