package com.example.appselecttest.data.models

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class MultimediaApiModel(
    @Json(name = "src") val urlImage: String
)