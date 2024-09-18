package com.sipay.baseapplicationcompose.ui.moviedetail

import com.sipay.baseapplicationcompose.base.BaseViewModel
import com.sipay.baseapplicationcompose.movieResult
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MovieDetailViewModel @Inject constructor() : BaseViewModel() {
    var result: movieResult? = null
}
