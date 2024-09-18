package com.sipay.baseapplicationcompose.ui.movie

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.sipay.baseapplicationcompose.base.BaseViewModel
import com.sipay.baseapplicationcompose.model.ResultData
import com.sipay.baseapplicationcompose.usecase.GetMoviesUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject
import com.sipay.baseapplicationcompose.model.Result


@HiltViewModel
class MovieViewModel @Inject constructor(private val getMoviesUseCase: GetMoviesUseCase) : BaseViewModel() {
    private var _movies =
        MutableLiveData<ResultData<MutableList<Result?>>>()
    val movies: LiveData<ResultData<MutableList<Result?>>>
        get() = _movies

    fun fetchMovies() {
        viewModelScope.launch(Dispatchers.IO) {
            getMoviesUseCase.invoke().collect {
                handleTask(it)
                _movies.postValue(it)
            }
        }
    }
}
