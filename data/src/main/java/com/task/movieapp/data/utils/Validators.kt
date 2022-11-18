package com.task.movieapp.data.utils

fun controlResponseCode(responseCode: Int): Boolean {
    if (responseCode in 200..299) {
        return true
    }
    return false
}


