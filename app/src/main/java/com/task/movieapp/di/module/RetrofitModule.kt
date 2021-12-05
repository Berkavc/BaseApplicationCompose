package com.task.movieapp.di.module

import com.task.movieapp.domain.BASE_URL
import com.task.movieapp.domain.retrofit.RetrofitClient
import com.task.movieapp.domain.retrofit.RetrofitRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityRetainedComponent
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import javax.inject.Singleton

@InstallIn(ActivityRetainedComponent::class)
@Module
object RetrofitModule {

    @Provides
    fun provideRetrofitClient(): Retrofit {
        return RetrofitClient().getRetrofitClient(BASE_URL)
    }

    @Provides
    fun provideRetrofitRepository(): RetrofitRepository {
        return provideRetrofitClient().create(RetrofitRepository::class.java)
    }


}