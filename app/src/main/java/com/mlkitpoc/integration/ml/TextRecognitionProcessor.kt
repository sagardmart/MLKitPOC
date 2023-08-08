package com.mlkitpoc.integration.ml

import com.google.mlkit.vision.common.InputImage
import com.google.mlkit.vision.text.Text
import com.google.mlkit.vision.text.TextRecognizer
import com.mlkitpoc.integration.ml.model.MLData
import com.mlkitpoc.integration.ml.model.MLResult
import com.mlkitpoc.integration.ml.model.MLTypes

class TextRecognitionProcessor(private val processor: TextRecognizer) : BaseProcessor() {

    private fun handleOnSuccess(result: Text?, onComplete: (MLResult) -> Unit) {
        val text = result?.text
        if (text.isNullOrBlank()) {
            onComplete(failureObject(MLTypes.MLTextRecognition.type))
            return
        }
        val textArray = text.split("\n")
        if (textArray.isNotEmpty()) {
            val mlResult = formatResult(textArray)
            onComplete(mlResult)
        } else {
            onComplete(failureObject(MLTypes.MLTextRecognition.type))
        }
    }

    private fun formatResult(textArray: List<String>): MLResult {
        val mlResult = MLResult()
        mlResult.type = MLTypes.MLTextRecognition.type
        val data = mutableListOf<MLData>()
        textArray.forEach {
            if (it.trim().isNotEmpty())
                data.add(MLData(it, 0f))
        }
        if (data.isNotEmpty()) {
            mlResult.isSuccess = true
            mlResult.data = data
        }
        return mlResult
    }

    override fun processImage(inputImage: InputImage, onComplete: (MLResult) -> Unit) {
        processor.process(inputImage).addOnCompleteListener {
            if (it.isSuccessful) {
                handleOnSuccess(it.result, onComplete)
            } else {
                onComplete(failureObject(MLTypes.MLTextRecognition.type, it.exception?.message))
            }
        }
    }

    override fun stopProcess() {
        processor.close()
    }
}