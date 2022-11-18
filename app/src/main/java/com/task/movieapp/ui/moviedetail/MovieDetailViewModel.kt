package com.task.movieapp.ui.moviedetail

import com.task.movieapp.common.base.BaseViewModel
import com.task.movieapp.common.movieResult
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MovieDetailViewModel @Inject constructor() : BaseViewModel() {
    var result: movieResult? = null
}
