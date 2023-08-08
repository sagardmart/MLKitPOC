package com.mlkitpoc.integration

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.mlkitpoc.databinding.ActivityIntegrationListBinding
import com.mlkitpoc.integration.viewmodel.ContentViewModel

class IntegrationListActivity : AppCompatActivity() {

    private lateinit var binding: ActivityIntegrationListBinding
    private val viewModel: ContentViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityIntegrationListBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val list = intent.getStringArrayListExtra("itemList") ?: listOf<String>()
        viewModel.setItemsList(list)
    }
}