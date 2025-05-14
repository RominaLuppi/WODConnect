package com.example.wodconnect.data

import com.example.wodconnect.modelo.CrossfitApiService
import com.example.wodconnect.modelo.MockLoginApiService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    @Provides
    fun provideBaseUrl() = "https://crossfit-wod-api.fly.dev/api/v1/"

    @Provides
    @Singleton
    fun provideRetrofit(baseUrl: String): Retrofit =
        Retrofit.Builder()
            .baseUrl(baseUrl)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

    @Provides
    @Singleton
    fun provideCrossfitApiService(retrofit: Retrofit): CrossfitApiService =
        retrofit.create(CrossfitApiService::class.java)

    @Provides
    @Singleton
    fun provideMockLoginApiService(): MockLoginApiService {
        return Retrofit.Builder()
            .baseUrl("https://run.mocky.io/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(MockLoginApiService::class.java)
    }
}