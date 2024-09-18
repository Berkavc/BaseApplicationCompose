package com.sipay.baseapplicationcompose.retrofit

import com.sipay.baseapplicationcompose.model.Movie
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface MovieRepository {
    @GET("search/movie")
    fun getMovies(
        @Query("language") language: String,
        @Query("page") page: Int,
        @Query("include_adult") includeAdult: Boolean,
        @Query("query") query: String
    ): Call<Movie?>
}