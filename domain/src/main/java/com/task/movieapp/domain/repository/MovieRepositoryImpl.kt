package com.task.movieapp.domain.repository

import com.task.movieapp.domain.API_KEY
import com.task.movieapp.domain.model.Movie
import com.task.movieapp.domain.model.Result
import com.task.movieapp.domain.model.ResultData
import com.task.movieapp.domain.retrofit.RetrofitRepository
import com.task.movieapp.domain.utils.controlResponseCode
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import retrofit2.Call
import retrofit2.Response
import javax.inject.Inject

@ExperimentalCoroutinesApi
class MovieRepositoryImpl @Inject constructor(private val retrofitRepository: RetrofitRepository) :
    MovieRepository {
    override fun getMovies(): Flow<ResultData<MutableList<Result>>> = callbackFlow {
        send(ResultData.Loading())
        try {
            retrofitRepository.getMovies(
                language = "en-US",
                page = 1,
                includeAdult = false,
                query = "10"
            ).enqueue(object : retrofit2.Callback<Movie?> {
                override fun onResponse(call: Call<Movie?>, response: Response<Movie?>) {
                    if (controlResponseCode(response.code())) {
                        response.body()?.let {
                            trySend(ResultData.Success(it.results.toMutableList()))
                        }
                    } else {
                        trySend(
                            ResultData.Failed(
                                errorMessage = response.errorBody().toString()
                            )
                        )
                    }
                }

                override fun onFailure(call: Call<Movie?>, t: Throwable) {
                    trySend(ResultData.Failed(errorMessage = t.message))
                }

            })
        } catch (e: Exception) {
            send(ResultData.Failed(errorMessage = e.message))
        }
        awaitClose { }
    }
}