package com.task.movieapp.domain.repository

import com.task.movieapp.domain.model.Result
import com.task.movieapp.domain.model.ResultData
import kotlinx.coroutines.flow.Flow

interface MoviesRepository {
    fun getMovies(): Flow<ResultData<MutableList<Result?>>>
}