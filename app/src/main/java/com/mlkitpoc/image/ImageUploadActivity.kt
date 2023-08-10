package com.mlkitpoc.image

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.widget.Button
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.google.android.material.snackbar.Snackbar
import com.mlkitpoc.R;

class ImageUploadActivity : AppCompatActivity() {

    companion object {
        public const val REQUEST_IMAGE_PICK = 1
        public const val REQUEST_CAMERA_CAPTURE = 2
        public const val CAMERA_PERMISSION_REQUEST_CODE = 3
        private var denied = false
    }

    private val takePicture = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
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
            if (hasCameraPermission()) {
                openCameraForListScan()
            }
            else {
                requestCameraPermission()
            }
        }
    }

    private val getContent = registerForActivityResult(ActivityResultContracts.GetContent()) { imageUri: Uri? ->
        if (imageUri != null) {
            startImageDisplayActivity(null, imageUri)
        }
    }

    private fun openGallery() {
        getContent.launch("image/*")
    }

    private fun openCameraForListScan() {
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

    private fun hasCameraPermission(): Boolean {
        return ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED
    }

    private fun requestCameraPermission() {
        if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.CAMERA)) {

            val snackbar = Snackbar.make(
                findViewById(R.id.rootLayout),
                "Camera permission is required to take pictures",
                Snackbar.LENGTH_INDEFINITE
            )
            snackbar.setAction("Grant") {
                ActivityCompat.requestPermissions(
                    this,
                    arrayOf(Manifest.permission.CAMERA),
                    CAMERA_PERMISSION_REQUEST_CODE
                )
            }
            snackbar.show()
        }
        else {
            ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.CAMERA), CAMERA_PERMISSION_REQUEST_CODE)
        }
    }


    // Handle permission request result
    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == CAMERA_PERMISSION_REQUEST_CODE) {
            if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                openCameraForListScan()
            } else {
                denied = true
            }
        }
    }
}
