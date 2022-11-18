package com.task.movieapp.data.repository.device

import com.task.movieapp.domain.model.Result
import com.task.movieapp.domain.model.ResultData
import com.task.movieapp.domain.repository.MoviesRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class MoviesRepositoryImpl @Inject constructor(
    private val localDataSource: MoviesRemoteDataSource
) : MoviesRepository {
    override fun getMovies(): Flow<ResultData<MutableList<Result?>>> = flow {
        emit(ResultData.Loading())
        try {
            emit(ResultData.Success(localDataSource.getMovies()))
        } catch (e: Exception) {
            emit(ResultData.Failed(errorMessage = e.message))
        }
    }
}