package com.example.appselecttest.di

import com.example.appselecttest.data.network.MovieNetworkApi
import com.example.appselecttest.data.repository.MovieRepositoryImpl
import com.example.appselecttest.domain.usecase.GetMovieUseCase
import com.example.appselecttest.ui.viewmodel.MovieViewModel
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

const val BASE_URL = "https://api.nytimes.com/"

val appModule = module {

    single {
        Moshi.Builder()
            .add(KotlinJsonAdapterFactory())
            .build()
    }

    single {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(MoshiConverterFactory.create(get<Moshi>()))
            .build()
    }

    single<MovieNetworkApi> {
        get<Retrofit>().create(MovieNetworkApi::class.java)
    }

    single<MovieRepositoryImpl> {
        MovieRepositoryImpl(get<MovieNetworkApi>())
    }

    single <GetMovieUseCase> {
        GetMovieUseCase(get<MovieRepositoryImpl>())
    }

    factory <MovieViewModel> {
        MovieViewModel(get<GetMovieUseCase>())
    }

}