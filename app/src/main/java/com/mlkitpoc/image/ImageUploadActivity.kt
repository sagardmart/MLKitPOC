package com.mlkitpoc.image

import android.app.Activity
import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.widget.Button
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import com.mlkitpoc.R

class ImageUploadActivity : AppCompatActivity() {

    companion object {
        private const val REQUEST_IMAGE_PICK = 1
        private const val REQUEST_CAMERA_CAPTURE = 2
    }

    private val takePicture =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == Activity.RESULT_OK) {
                val data: Intent? = result.data
                val imageBitmap = data?.extras?.get("data") as Bitmap?
                startImageDisplayActivity(imageBitmap, null)
            }
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_image_upload)

        findViewById<Button>(R.id.galleryButton).setOnClickListener {
            openGallery()
        }

        findViewById<Button>(R.id.cameraButton).setOnClickListener {
            openCameraForListScan()
        }
    }

    private val getContent =
        registerForActivityResult(ActivityResultContracts.GetContent()) { imageUri: Uri? ->
            if (imageUri != null) {
                startImageDisplayActivity(null, imageUri)
            }
        }


    private fun openGallery() {
        getContent.launch("image/*")
    }

    private fun openCameraForListScan() {
        //permission
        val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        takePicture.launch(intent)
    }

    private fun startImageDisplayActivity(imageBitmap: Bitmap?, imageUri: Uri?) {
        val intent = Intent(this, ImageDisplayActivity::class.java).apply {
            putExtra(ImageDisplayActivity.EXTRA_IMAGE_BITMAP, imageBitmap)
            putExtra(ImageDisplayActivity.EXTRA_IMAGE_URI, imageUri)
        }
        startActivity(intent)
    }
}
