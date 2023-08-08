//package com.example.uploadfeature
//
//import android.graphics.Bitmap
//import android.os.Bundle
//import android.widget.ImageView
//import androidx.appcompat.app.AppCompatActivity
//
//class ImageDisplayActivity : AppCompatActivity() {
//
//    companion object {
//        const val EXTRA_IMAGE_BITMAP = "extra_image_bitmap"
//    }
//
//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//        setContentView(R.layout.activity_image_display)
//
//        val imageBitmap = intent.getParcelableExtra<Bitmap>(EXTRA_IMAGE_BITMAP)
//
//        val imageView = findViewById<ImageView>(R.id.displayImageView)
//        imageView.setImageBitmap(imageBitmap)
//    }
//}

package com.mlkitpoc.image

import android.graphics.Bitmap
import android.net.Uri
import android.os.Bundle
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import com.mlkitpoc.R

class ImageDisplayActivity : AppCompatActivity() {

    companion object {
        const val EXTRA_IMAGE_BITMAP = "extra_image_bitmap"
        const val EXTRA_IMAGE_URI = "extra_image_uri"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_image_display)

        val imageBitmap = intent.getParcelableExtra<Bitmap>(EXTRA_IMAGE_BITMAP)
        val imageUri = intent.getParcelableExtra<Uri>(EXTRA_IMAGE_URI)

        val imageView = findViewById<ImageView>(R.id.displayImageView)

        if (imageBitmap != null) {
            imageView.setImageBitmap(imageBitmap)
        } else if (imageUri != null) {
            imageView.setImageURI(imageUri)
        }
    }
}
