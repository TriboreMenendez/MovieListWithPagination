package com.example.appselecttest.data.models

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class ResponseApiModel(
    @Json(name = "has_more") val moreResult: Boolean,
    val results: List<MovieApiModel>
)