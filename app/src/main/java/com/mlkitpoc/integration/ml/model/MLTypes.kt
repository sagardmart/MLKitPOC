package com.mlkitpoc.integration.ml.model

sealed class MLTypes(var type: String, var displayText: String) {
    object MLTextRecognition : MLTypes("text", "Text Recognition Latin")
    object MLImageLabel : MLTypes("imageLabel", "Image Labeling")
}