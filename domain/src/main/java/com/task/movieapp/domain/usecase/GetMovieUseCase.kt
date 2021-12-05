package com.task.movieapp.domain.usecase

import com.task.movieapp.domain.model.Result
import com.task.movieapp.domain.model.ResultData
import com.task.movieapp.domain.repository.MovieRepositoryImpl
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetMovieUseCase @Inject constructor(private val movieRepositoryImpl: MovieRepositoryImpl) {
    operator fun invoke(): Flow<ResultData<MutableList<Result>>> {
       return movieRepositoryImpl.getMovies()
    }
}