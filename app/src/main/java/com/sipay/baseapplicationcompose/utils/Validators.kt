package com.sipay.baseapplicationcompose.utils

fun controlResponseCode(responseCode: Int): Boolean {
    return responseCode in 200..299
}


