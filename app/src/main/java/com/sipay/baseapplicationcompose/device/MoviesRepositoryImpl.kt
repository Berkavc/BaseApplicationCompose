package com.sipay.baseapplicationcompose.device

import com.sipay.baseapplicationcompose.model.Result
import com.sipay.baseapplicationcompose.model.ResultData
import com.sipay.baseapplicationcompose.repository.MoviesRepository
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