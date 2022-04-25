package com.example.appselecttest.data.network

import com.example.appselecttest.BuildConfig
import com.example.appselecttest.data.models.ResponseApiModel
import retrofit2.http.GET
import retrofit2.http.Query

// Api-key: quuhuxLRGZzKVHGz7319OETIDq6cksGi
// https://api.nytimes.com/svc/movies/v2/reviews/all.json?api-key=API_KEY

val API_KEY: String = BuildConfig.API_KEY

interface MovieNetworkApi {
    @GET("svc/movies/v2/reviews/all.json")

    suspend fun getMovie(
        @Query("offset") itemPage: Int, // Sets the starting point of the result set. Needs to be multiple of 20.
        @Query("api-key") apiKey: String = API_KEY
    ) : ResponseApiModel
}