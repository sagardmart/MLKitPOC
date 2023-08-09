package com.mlkitpoc.integration

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.EditText
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.mlkitpoc.R
import com.mlkitpoc.databinding.ActivityIntegrationMainBinding
import com.mlkitpoc.databinding.ActivityMainBinding
import com.mlkitpoc.list.ListActivity
import com.mlkitpoc.main.MainAdapter

@SuppressLint("NotifyDataSetChanged")
class IntegrationMainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityIntegrationMainBinding

    var list = ArrayList<String>()
    private var adapter: MainAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityIntegrationMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setLayout(binding.mainLayout)

    }

    private fun setLayout(binding: ActivityMainBinding) {
        binding.recyclerMainList.layoutManager =
                LinearLayoutManager(this, RecyclerView.VERTICAL, false)
        adapter = MainAdapter(list)
        binding.recyclerMainList.adapter = adapter

        binding.addItemButton.setOnClickListener {
            val builder = AlertDialog.Builder(this)
            val inflater = layoutInflater
            builder.setTitle("Add Item")
            builder.setNeutralButton("Cancel") { dialog, i -> }
            val dialogLayout = inflater.inflate(R.layout.add_item_popup, null)
            val editText = dialogLayout.findViewById<EditText>(R.id.editText)
            builder.setView(dialogLayout)
            builder.setPositiveButton("OK") { dialog, i ->
                list.add(editText.text.toString())
                adapter?.notifyDataSetChanged()
            }
            builder.show()
        }

        binding.clearButton.setOnClickListener{
            list.clear()
            adapter?.notifyDataSetChanged()
        }

        binding.scanButton.setOnClickListener {
            val intent = Intent(this@IntegrationMainActivity, IntegrationImageUploadActivity::class.java)
            listResultLauncher.launch(intent)
        }

        binding.doneButton.setOnClickListener {
            val launchList = Intent(this, IntegrationListActivity::class.java)
            launchList.putStringArrayListExtra("itemList", list)
            startActivity(launchList)
        }
    }

    private var listResultLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
        if (result.resultCode == Activity.RESULT_OK) {
            // There are no request codes
            val resultList = result.data?.getStringArrayListExtra("itemList")
            if (!resultList.isNullOrEmpty()) {
                list.addAll(resultList)
                adapter?.notifyDataSetChanged()
            }
        }
    }
}