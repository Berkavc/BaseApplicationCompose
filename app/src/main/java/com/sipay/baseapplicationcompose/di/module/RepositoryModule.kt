package com.sipay.baseapplicationcompose.di.module

import android.content.Context
import com.sipay.baseapplicationcompose.campaigns.MoviesRemoteDataSourceImpl
import com.sipay.baseapplicationcompose.campaigns.MoviesRemoteImpl
import com.sipay.baseapplicationcompose.device.MoviesRemoteDataSource
import com.sipay.baseapplicationcompose.device.MoviesRepositoryImpl
import com.sipay.baseapplicationcompose.di.module.RetrofitModule.provideRetrofitMovieRepository
import com.sipay.baseapplicationcompose.repository.MoviesRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
object RepositoryModule {
    @Provides
    @Singleton
    fun providesMoviesRepository(
        remoteDataSource: MoviesRemoteDataSource
    ): MoviesRepository {
        return MoviesRepositoryImpl(remoteDataSource)
    }

    @Provides
    @Singleton
    fun providesMoviesRemoteImpl(): MoviesRemoteImpl {
        return MoviesRemoteImpl(provideRetrofitMovieRepository())
    }

    @Provides
    @Singleton
    fun providesMoviesRemoteDataSource(@ApplicationContext context: Context): MoviesRemoteDataSource {
        return MoviesRemoteDataSourceImpl(providesMoviesRemoteImpl())
    }
}