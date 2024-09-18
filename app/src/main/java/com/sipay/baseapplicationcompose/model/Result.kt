package com.sipay.baseapplicationcompose.model

import java.io.Serializable
data class Result(
    val adult: Boolean? = null,
    val backdrop_path: String? = null,
    val genre_ids: List<Int?> = listOf(),
    val id: Int,
    val original_language: String? = null,
    val original_title: String? = null,
    val overview: String? = null,
    val popularity: Double = 0.0,
    val poster_path: String? = null,
    val release_date: String? = null,
    val title: String,
    val video: Boolean = false,
    val vote_average: Double= 0.0,
    val vote_count: Int = 0
):Serializable