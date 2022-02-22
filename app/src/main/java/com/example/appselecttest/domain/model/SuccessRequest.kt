package com.example.appselecttest.domain.model

data class SuccessRequest(
    val listResult: List<MovieDomainModel> = listOf(),
    val error: Exception? = null
)