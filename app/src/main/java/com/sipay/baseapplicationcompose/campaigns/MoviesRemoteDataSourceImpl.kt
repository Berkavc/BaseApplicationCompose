package com.sipay.baseapplicationcompose.campaigns

import com.sipay.baseapplicationcompose.device.MoviesRemoteDataSource
import com.sipay.baseapplicationcompose.movieResult
import javax.inject.Inject
import kotlin.coroutines.resumeWithException
import kotlin.coroutines.suspendCoroutine

class MoviesRemoteDataSourceImpl @Inject constructor(private val moviesRemoteImpl: MoviesRemoteImpl) :
    MoviesRemoteDataSource {
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