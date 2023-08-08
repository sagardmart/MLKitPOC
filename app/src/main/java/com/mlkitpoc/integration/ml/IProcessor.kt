package com.mlkitpoc.integration.ml

import android.content.ContentResolver
import android.graphics.Bitmap
import android.net.Uri
import com.google.mlkit.vision.common.InputImage
import com.mlkitpoc.integration.ml.model.MLResult

interface IProcessor {

    fun processImage(inputImage: InputImage, onComplete: (MLResult) -> Unit)

    fun stopProcess()

    fun getResizedBitmap(imageUri: Uri?, imageMaxWidth: Int, imageMaxHeight: Int, contentResolver: ContentResolver?): Bitmap?

    fun getResizedBitmap(imageBitmap: Bitmap?, imageMaxWidth: Int, imageMaxHeight: Int): Bitmap?
}