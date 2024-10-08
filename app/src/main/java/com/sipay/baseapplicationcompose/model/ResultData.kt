package com.sipay.baseapplicationcompose.model

sealed class ResultData<out T> {

    data class Success<out T>(val data: T? = null) : ResultData<T>()

    data class Loading(
        val loadingResourceId: Int? = null,
        val loadingMessage: String? = null,
        val progress: Int = 0
    ) : ResultData<Nothing>()

    data class Failed(
        val exception: Exception? = null,
        val errorTitle: String? = null,
        val errorMessage: String? = null,
        val buttonTitle: String? = null,
        var callback: () -> Unit? = { },
    ) : ResultData<Nothing>()

    fun toData(): T? = if (this is Success) this.data else null
}