package com.mlkitpoc.integration.ml.model

import com.google.gson.annotations.SerializedName

data class MLResult(
        @SerializedName("type")
        var type: String = MLTypes.MLTextRecognition.type,

        @SerializedName("isSuccess")
        var isSuccess: Boolean = false,

        @SerializedName("message")
        var message: String = "",

        @SerializedName("data")
        var data: List<MLData> = listOf()
)

data class MLData(
        @SerializedName("text")
        var text: String = "",

        @SerializedName("confidence")
        var confidence: Float = 0f
)
