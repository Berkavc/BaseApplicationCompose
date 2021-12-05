package com.task.movieapp.domain.retrofit

import com.task.movieapp.domain.MOVIE
import com.task.movieapp.domain.SEARCH
import com.task.movieapp.domain.model.Movie
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface RetrofitRepository {
    @GET(SEARCH + MOVIE)
    fun getMovies(
        @Query("language") language: String,
        @Query("page") page: Int,
        @Query("include_adult") includeAdult: Boolean,
        @Query("query") query: String
    ): Call<Movie?>
}