package com.task.movieapp.di.module

import android.content.Context
import com.task.movieapp.data.framework.campaigns.MoviesRemoteDataSourceImpl
import com.task.movieapp.data.framework.campaigns.MoviesRemoteImpl
import com.task.movieapp.data.repository.device.MoviesRemoteDataSource
import com.task.movieapp.data.repository.device.MoviesRepositoryImpl
import com.task.movieapp.di.module.RetrofitModule.provideRetrofitMovieRepository
import com.task.movieapp.domain.repository.MoviesRepository
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