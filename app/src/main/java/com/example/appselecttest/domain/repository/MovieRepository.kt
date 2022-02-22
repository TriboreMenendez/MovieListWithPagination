package com.example.appselecttest.domain.repository

import com.example.appselecttest.domain.model.SuccessRequest

interface MovieRepository {
    suspend fun getMovie(): SuccessRequest
}