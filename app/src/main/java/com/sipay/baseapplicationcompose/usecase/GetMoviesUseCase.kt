package com.sipay.baseapplicationcompose.usecase

import com.sipay.baseapplicationcompose.model.Result
import com.sipay.baseapplicationcompose.model.ResultData
import com.sipay.baseapplicationcompose.repository.MoviesRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetMoviesUseCase @Inject constructor(private val moviesRepository: MoviesRepository) {
    operator fun invoke(): Flow<ResultData<MutableList<Result?>>> {
       return moviesRepository.getMovies()
    }
}