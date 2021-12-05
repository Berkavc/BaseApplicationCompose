package com.task.movieapp

import com.task.movieapp.common.utils.convertLongToDateFormat
import org.junit.Test

import org.junit.Assert.*

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class MovieAppUnitTest {
    @Test
    fun addition_isCorrect() {
        assertEquals(4, 2 + 2)
    }
    @Test
    fun dateRule() {
        val dateLong = System.currentTimeMillis()
        val formattedDate = convertLongToDateFormat(dateLong)
        assertEquals(true, !formattedDate.isNullOrEmpty())
        assertEquals(false, convertLongToDateFormat(dateLong).contains("/"))
    }
}
