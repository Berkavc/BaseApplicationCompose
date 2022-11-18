package com.task.movieapp.domain.usecase

import com.task.movieapp.domain.model.Result
import com.task.movieapp.domain.model.ResultData
import com.task.movieapp.domain.repository.MoviesRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetMoviesUseCase @Inject constructor(private val moviesRepository: MoviesRepository) {
    operator fun invoke(): Flow<ResultData<MutableList<Result?>>> {
       return moviesRepository.getMovies()
    }
}