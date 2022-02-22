package com.example.appselecttest.data.repository


import com.example.appselecttest.data.network.MovieNetworkApi
import com.example.appselecttest.data.toDomain
import com.example.appselecttest.domain.model.SuccessRequest
import com.example.appselecttest.domain.repository.MovieRepository

class MovieRepositoryImpl(private val movieNetwork: MovieNetworkApi) : MovieRepository {

    // Check if there are any more results
    private var moreResult = true

    // Keep the last requested page. When the request is successful, increment the page number
    private var lastRequestedPage = 0

    // Avoid triggering multiple requests in the same time
    private var isRequestInProgress = false

    override suspend fun getMovie(): SuccessRequest {
        if (moreResult && !isRequestInProgress) {
            isRequestInProgress = true
            return try {
                val response = movieNetwork.getMovie(lastRequestedPage)
                val moviesResult = response.results.map { it.toDomain() }
                if (response.moreResult) lastRequestedPage += 20 else moreResult = false
                isRequestInProgress = false
                SuccessRequest(moviesResult)
            } catch (exception: Exception) {
                isRequestInProgress = false
                SuccessRequest(error = exception)
            }
        } else return SuccessRequest()
    }
}

