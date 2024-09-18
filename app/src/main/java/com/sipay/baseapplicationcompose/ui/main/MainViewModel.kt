package com.sipay.baseapplicationcompose.ui.main

import androidx.lifecycle.viewModelScope
import com.sipay.baseapplicationcompose.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor() : BaseViewModel(){
    private val _isLoading = MutableStateFlow(true)
    val isLoading = _isLoading.asStateFlow()

    //TODO DO Splash actions!
    init {
        viewModelScope.launch(Dispatchers.IO) {
            delay(1500)
            _isLoading.value = false
        }
    }
}
