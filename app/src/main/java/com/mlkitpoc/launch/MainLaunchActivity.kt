package com.mlkitpoc.launch

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.mlkitpoc.databinding.ActivityLaunchBinding
import com.mlkitpoc.image.ImageUploadActivity
import com.mlkitpoc.integration.IntegrationMainActivity
import com.mlkitpoc.list.ListActivity
import com.mlkitpoc.main.MainActivity

class MainLaunchActivity : AppCompatActivity() {
    private lateinit var binding: ActivityLaunchBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLaunchBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnIntegration.setOnClickListener {
            startActivity(Intent(this@MainLaunchActivity, IntegrationMainActivity::class.java))
        }

        binding.btnMainList.setOnClickListener {
            startActivity(Intent(this@MainLaunchActivity, MainActivity::class.java))
        }

        binding.btnDetails.setOnClickListener {
            startActivity(Intent(this@MainLaunchActivity, ListActivity::class.java))
        }

        binding.btnImage.setOnClickListener {
            startActivity(Intent(this@MainLaunchActivity, ImageUploadActivity::class.java))
        }
    }
}