package com.task.movieapp.di.module

import com.task.movieapp.data.BASE_URL
import com.task.movieapp.data.retrofit.MovieRepository
import com.task.movieapp.data.retrofit.RetrofitClient
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