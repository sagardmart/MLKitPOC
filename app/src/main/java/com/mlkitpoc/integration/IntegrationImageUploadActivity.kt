package com.mlkitpoc.integration

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.view.View
import android.view.ViewTreeObserver
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.google.android.material.snackbar.Snackbar
import com.google.mlkit.vision.common.InputImage
import com.google.mlkit.vision.label.ImageLabel
import com.google.mlkit.vision.label.ImageLabeler
import com.google.mlkit.vision.label.ImageLabeling
import com.google.mlkit.vision.label.defaults.ImageLabelerOptions
import com.google.mlkit.vision.text.TextRecognition
import com.google.mlkit.vision.text.latin.TextRecognizerOptions
import com.mlkitpoc.R
import com.mlkitpoc.databinding.ActivityIntegrationImageUploadBinding
import com.mlkitpoc.image.ImageDisplayActivity
import com.mlkitpoc.image.ImageUploadActivity
import com.mlkitpoc.integration.ml.IProcessor
import com.mlkitpoc.integration.ml.ImageLabelerProcessor
import com.mlkitpoc.integration.ml.TextRecognitionProcessor
import com.mlkitpoc.integration.ml.model.MLData

class IntegrationImageUploadActivity : AppCompatActivity() {
    private lateinit var binding: ActivityIntegrationImageUploadBinding

    private var imageBitmap: Bitmap? = null
    private var imageUri: Uri? = null

    // Max width (portrait mode)
    private var imageMaxWidth = 0

    // Max height (portrait mode)
    private var imageMaxHeight = 0


    private val takePicture =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == Activity.RESULT_OK) {
                val data: Intent? = result.data
                val imageBitmap = data?.extras?.get("data") as Bitmap?
                startImageDisplayActivity(imageBitmap, null)
            }
        }

    private val getContent =
        registerForActivityResult(ActivityResultContracts.GetContent()) { imageUri: Uri? ->
            if (imageUri != null) {
                startImageDisplayActivity(null, imageUri)
            }
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityIntegrationImageUploadBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.galleryButton.setOnClickListener {
            openGallery()
        }

        binding.cameraButton.setOnClickListener {
            if (hasCameraPermission()) {
                openCameraForListScan()
            } else {
                requestCameraPermission()
            }
        }

        binding.textScan.setOnClickListener {
            val processor =
                TextRecognitionProcessor(TextRecognition.getClient(TextRecognizerOptions.DEFAULT_OPTIONS))
            if (imageBitmap != null) {
                imageBitmap = processor.getResizedBitmap(imageBitmap, imageMaxWidth, imageMaxHeight)
            } else if (imageUri != null) {
                imageBitmap = processor.getResizedBitmap(
                    imageUri,
                    imageMaxWidth,
                    imageMaxHeight,
                    contentResolver
                )
            }
            processBitmapImage(processor)
        }

        binding.imgLabelScan.setOnClickListener {
            val processor =
                ImageLabelerProcessor(ImageLabeling.getClient(ImageLabelerOptions.DEFAULT_OPTIONS))
            if (imageBitmap != null) {
                imageBitmap = processor.getResizedBitmap(imageBitmap, imageMaxWidth, imageMaxHeight)
            } else if (imageUri != null) {
                imageBitmap = processor.getResizedBitmap(
                    imageUri,
                    imageMaxWidth,
                    imageMaxHeight,
                    contentResolver
                )
            }
            processBitmapImage(processor)
        }
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
                    ImageUploadActivity.CAMERA_PERMISSION_REQUEST_CODE
                )
            }
            snackbar.show()
        } else {
            ActivityCompat.requestPermissions(
                this, arrayOf(Manifest.permission.CAMERA),
                ImageUploadActivity.CAMERA_PERMISSION_REQUEST_CODE
            )
        }

    }

    private fun hasCameraPermission(): Boolean {
        return ContextCompat.checkSelfPermission(
            this,
            Manifest.permission.CAMERA
        ) == PackageManager.PERMISSION_GRANTED
    }

    private fun processBitmapImage(processor: IProcessor?) {
        if (imageBitmap != null) {
            val inputImage = InputImage.fromBitmap(imageBitmap!!, 0)
            processor?.processImage(inputImage) {
                Log.d("TAG", "processBitmapImage: ${it.data}")
                if (it.isSuccess) {
                    val list = getStringList(it.data)
                    val intent = Intent()
                    intent.putStringArrayListExtra("itemList", ArrayList(list))
                    setResult(Activity.RESULT_OK, intent)
                    finish()
                }
                Toast.makeText(
                    this@IntegrationImageUploadActivity,
                    "${it.isSuccess} ${it.message}",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
    }

    private fun getStringList(mlData: List<MLData>): List<String> {
        return mlData.map {
            it.text
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
        this.imageBitmap = imageBitmap
        this.imageUri = imageUri
        if (imageUri != null) {
            binding.imageView.setImageURI(imageUri)
            binding.mlBtnLayout.visibility = View.VISIBLE
        } else if (imageBitmap != null) {
            binding.imageView.setImageBitmap(imageBitmap)
            binding.mlBtnLayout.visibility = View.VISIBLE
        } else {
            binding.mlBtnLayout.visibility = View.GONE
        }

        binding.root.viewTreeObserver.addOnGlobalLayoutListener(object :
            ViewTreeObserver.OnGlobalLayoutListener {
            override fun onGlobalLayout() {
                binding.root.viewTreeObserver.removeOnGlobalLayoutListener(this)
                imageMaxWidth = binding.root.width
                imageMaxHeight = binding.root.height - binding.imageView.height
            }
        })
    }

    // Handle permission request result
    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == ImageUploadActivity.CAMERA_PERMISSION_REQUEST_CODE) {
            if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                openCameraForListScan()
            }
        }
    }

}
