package com.sipay.baseapplicationcompose.di.module

import com.sipay.baseapplicationcompose.BASE_URL
import com.sipay.baseapplicationcompose.retrofit.MovieRepository
import com.sipay.baseapplicationcompose.retrofit.RetrofitClient
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit

@InstallIn(SingletonComponent::class)
@Module
object RetrofitModule {
    @Provides
    fun provideRetrofitClient(): Retrofit {
        return RetrofitClient().getRetrofitClient(BASE_URL)
    }

    @Provides
    fun provideRetrofitMovieRepository(): MovieRepository {
        return provideRetrofitClient().create(MovieRepository::class.java)
    }
}