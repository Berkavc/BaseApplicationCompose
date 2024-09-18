package com.sipay.baseapplicationcompose.device

import com.sipay.baseapplicationcompose.model.Result

interface MoviesRemoteDataSource {
    suspend fun getMovies(): MutableList<Result?>
}