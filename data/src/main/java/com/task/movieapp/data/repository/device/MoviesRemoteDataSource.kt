package com.task.movieapp.data.repository.device
import com.task.movieapp.domain.model.Result

interface MoviesRemoteDataSource {
    suspend fun getMovies(): MutableList<Result?>
}