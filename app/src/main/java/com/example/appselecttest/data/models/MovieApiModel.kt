package com.example.appselecttest.data.models

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class MovieApiModel(
    @Json(name = "display_title")    val displayTitle: String,
    @Json(name = "publication_date") val publicationDate: String,
    @Json(name = "summary_short") val summaryShort: String,
    @Json(name = "multimedia") val multimedia: MultimediaApiModel,
)