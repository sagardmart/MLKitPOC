package com.mlkitpoc.integration.ml

import com.google.mlkit.vision.common.InputImage
import com.google.mlkit.vision.label.ImageLabel
import com.google.mlkit.vision.label.ImageLabeler
import com.mlkitpoc.integration.ml.model.MLData
import com.mlkitpoc.integration.ml.model.MLResult
import com.mlkitpoc.integration.ml.model.MLTypes

class ImageLabelerProcessor(private val processor: ImageLabeler) : BaseProcessor() {

    private fun handleOnSuccess(result: List<ImageLabel>, onComplete: (MLResult) -> Unit) {
        if (result.isNotEmpty()) {
            val mlResult = formatResult(result)
            onComplete(mlResult)
        } else {
            onComplete(failureObject(MLTypes.MLImageLabel.type))
        }
    }

    private fun formatResult(result: List<ImageLabel>): MLResult {
        val mlResult = MLResult()
        mlResult.type = MLTypes.MLImageLabel.type
        val data = mutableListOf<MLData>()
        result.forEach {
            if (it.text.trim().isNotEmpty()) {
                data.add(MLData(it.text, it.confidence))
            }
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
                onComplete(failureObject(MLTypes.MLImageLabel.type, it.exception?.message))
            }
        }
    }

    override fun stopProcess() {
        processor.close()
    }
}