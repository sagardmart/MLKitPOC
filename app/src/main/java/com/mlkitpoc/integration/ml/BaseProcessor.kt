package com.mlkitpoc.integration.ml

import android.content.ContentResolver
import android.graphics.Bitmap
import android.net.Uri
import android.util.Pair
import com.mlkitpoc.integration.ml.utils.BitmapUtilsKT
import com.mlkitpoc.integration.ml.model.MLResult

abstract class BaseProcessor : IProcessor {

    override fun getResizedBitmap(imageUri: Uri?, imageMaxWidth: Int, imageMaxHeight: Int, contentResolver: ContentResolver?): Bitmap? {
        if (imageUri == null) {
            return null
        }

        if (imageMaxWidth == 0) {
            // UI layout has not finished yet, will reload once it's ready.
            return null
        }

        val imageBitmap = BitmapUtilsKT.getBitmapFromContentUri(contentResolver, imageUri)
                ?: return null

        return getResizedBitmap(imageBitmap, imageMaxWidth, imageMaxHeight)
    }

    override fun getResizedBitmap(imageBitmap: Bitmap?, imageMaxWidth: Int, imageMaxHeight: Int): Bitmap? {
        if (imageBitmap == null) return null
        // Get the dimensions of the image view
        val targetedSize: Pair<Int, Int> = Pair(imageMaxWidth, imageMaxHeight)

        // Determine how much to scale down the image
        val scaleFactor = Math.max(imageBitmap.width.toFloat() / targetedSize.first.toFloat(), imageBitmap.height.toFloat() / targetedSize.second.toFloat())
        return Bitmap.createScaledBitmap(imageBitmap, (imageBitmap.width / scaleFactor).toInt(), (imageBitmap.height / scaleFactor).toInt(), true)
    }

    protected fun failureObject(type: String, message: String? = "NO DATA FOUND"): MLResult {
        val mlResult = MLResult()
        mlResult.type = type
        mlResult.isSuccess = false
        mlResult.message = message ?: "NO DATA FOUND"
        return mlResult
    }
}