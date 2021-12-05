package com.task.movieapp

import androidx.test.platform.app.InstrumentationRegistry
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.task.movieapp.domain.BASE_URL
import com.task.movieapp.domain.model.Movie
import com.task.movieapp.domain.model.ResultData
import com.task.movieapp.domain.retrofit.RetrofitClient
import com.task.movieapp.domain.retrofit.RetrofitRepository
import com.task.movieapp.domain.utils.controlResponseCode
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.launch

import org.junit.Test
import org.junit.runner.RunWith

import org.junit.Assert.*
import retrofit2.Call
import retrofit2.Response

/**
 * Instrumented test, which will execute on an Android device.
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
@RunWith(AndroidJUnit4::class)
class ExampleInstrumentedTest {
    @Test
    fun useAppContext() {
        // Context of the app under test.
        val appContext = InstrumentationRegistry.getInstrumentation().targetContext
        assertEquals("com.task.movieapp", appContext.packageName)
    }

}

@RunWith(AndroidJUnit4::class)
class UseCaseUnitTest {
    private val retrofitRepository =
        RetrofitClient().getRetrofitClient(BASE_URL).create(RetrofitRepository::class.java)

    @Test
    fun movieAppGetTest() {
        val response = retrofitRepository.getMovies(
            language = "en-US",
            page = 1,
            includeAdult = false,
            query = "10"
        ).execute()
        assertNotNull(response.body())
        assertEquals(200, response.code())
    }
}

