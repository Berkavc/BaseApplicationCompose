package com.task.movieapp.ui.movie

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.task.movieapp.common.base.BaseViewModel
import com.task.movieapp.domain.model.Result
import com.task.movieapp.domain.model.ResultData
import com.task.movieapp.domain.usecase.GetMovieUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class MovieViewModel @Inject constructor(
    private val getMovieUseCase: GetMovieUseCase
) : BaseViewModel() {

    private var _movieList =
        MutableLiveData<ResultData<MutableList<Result>>>()
    val movieList: LiveData<ResultData<MutableList<Result>>>
        get() = _movieList

    fun fetchMoviesList() {
        viewModelScope.launch(Dispatchers.IO) {
            getMovieUseCase.invoke().collect {
                handleTask(it)
                _movieList.postValue(it)
            }
        }
    }

}
