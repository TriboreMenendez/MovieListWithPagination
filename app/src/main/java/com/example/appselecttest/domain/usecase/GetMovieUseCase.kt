package com.example.appselecttest.domain.usecase

import com.example.appselecttest.domain.model.SuccessRequest
import com.example.appselecttest.domain.repository.MovieRepository

class GetMovieUseCase(private val movieRepo: MovieRepository) {

    suspend fun getMovie(): SuccessRequest {
        return movieRepo.getMovie()
    }
}