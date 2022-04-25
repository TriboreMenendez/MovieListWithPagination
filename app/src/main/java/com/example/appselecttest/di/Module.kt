package com.example.appselecttest.di

import com.example.appselecttest.data.network.MovieNetworkApi
import com.example.appselecttest.data.repository.MovieRepositoryImpl
import com.example.appselecttest.domain.usecase.GetMovieUseCase
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import javax.inject.Singleton

const val BASE_URL = "https://api.nytimes.com/"

@Module
@InstallIn(SingletonComponent::class)
class Module {

    @Provides
    @Singleton
    fun provideMoshi() = Moshi.Builder()
        .add(KotlinJsonAdapterFactory())
        .build()

    @Provides
    @Singleton
    fun provideRetrofit(moshi: Moshi) = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(MoshiConverterFactory.create(moshi))
        .build()

    @Provides
    @Singleton
    fun provideNetworkApi(retrofit: Retrofit): MovieNetworkApi {
        return retrofit.create(MovieNetworkApi::class.java)
    }

    @Provides
    @Singleton
    fun provideRepository(networkApi: MovieNetworkApi) = MovieRepositoryImpl(networkApi)

    @Provides
    @Singleton
    fun provideGetMovieUseCase(repo: MovieRepositoryImpl) = GetMovieUseCase(repo)
}