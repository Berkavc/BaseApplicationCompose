package com.sipay.baseapplicationcompose.campaigns

import com.sipay.baseapplicationcompose.Work
import com.sipay.baseapplicationcompose.retrofit.MovieRepository
import com.sipay.baseapplicationcompose.utils.controlResponseCode
import com.sipay.baseapplicationcompose.model.Movie
import com.sipay.baseapplicationcompose.movieResult
import retrofit2.Call
import retrofit2.Response
import javax.inject.Inject

class MoviesRemoteImpl @Inject constructor(private val movieRepository: MovieRepository) {
    fun getMovies(): Work<MutableList<movieResult?>> {
        val work = Work<MutableList<movieResult?>>()
        try {
            movieRepository.getMovies(
                language = "en-US",
                page = 1,
                includeAdult = false,
                query = "10"
            ).enqueue(object : retrofit2.Callback<Movie?> {
                override fun onResponse(call: Call<Movie?>, response: Response<Movie?>) {
                    if (controlResponseCode(response.code())) {
                        response.body()?.let {
                            work.onSuccess(it.results.toMutableList())
                        }
                    } else {
                        work.onFailure(error = Exception(response.errorBody().toString()))
                    }
                }

                override fun onFailure(call: Call<Movie?>, t: Throwable) {
                    work.onFailure(error = Exception(t.message))
                }

            })

        } catch (e: Exception) {
            work.onFailure(e)
        }
        return work
    }
}