package com.example.weatherme.models

import com.google.gson.annotations.SerializedName

// {"error":{"code":1003,"message":"Parameter q is missing."}}
data class ErrorResponse(@SerializedName("error") var error: ErrorBody)
data class ErrorBody(@SerializedName("code") val code: Int, @SerializedName("message") val message: String)