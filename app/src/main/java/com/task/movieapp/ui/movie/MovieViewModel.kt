package com.task.movieapp.ui.movie

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.task.movieapp.common.base.BaseViewModel
import com.task.movieapp.domain.model.Result
import com.task.movieapp.domain.model.ResultData
import com.task.movieapp.domain.usecase.GetMoviesUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject


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
