package com.sipay.baseapplicationcompose.model

import java.io.Serializable
data class Movie(
    val page: Int,
    val results: List<Result>,
    val total_pages: Int,
    val total_results: Int
): Serializable