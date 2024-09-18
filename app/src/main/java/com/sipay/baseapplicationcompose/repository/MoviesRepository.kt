package com.sipay.baseapplicationcompose.repository

import com.sipay.baseapplicationcompose.model.Result
import com.sipay.baseapplicationcompose.model.ResultData
import kotlinx.coroutines.flow.Flow

interface MoviesRepository {
    fun getMovies(): Flow<ResultData<MutableList<Result?>>>
}