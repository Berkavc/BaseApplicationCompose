package com.task.movieapp.data.framework.campaigns

import com.task.movieapp.common.movieResult
import com.task.movieapp.data.repository.device.MoviesRemoteDataSource
import javax.inject.Inject
import kotlin.coroutines.resumeWithException
import kotlin.coroutines.suspendCoroutine

class MoviesRemoteDataSourceImpl @Inject constructor(private val moviesRemoteImpl: MoviesRemoteImpl) : MoviesRemoteDataSource {
    override suspend fun getMovies(): MutableList<movieResult?> {
        return suspendCoroutine { continuation ->
            moviesRemoteImpl.getMovies()
                .addOnSuccessListener {
                    continuation.resumeWith(Result.success(it))
                }.addOnFailureListener {
                    continuation.resumeWithException(it)
                }
        }
    }
}