package com.ozantopuz.rijksmuseumapp.data

sealed class Result<out R> {

    data class Success<out T>(val data: T) : Result<T>()
    data class Error(val exception: Exception) : Result<Nothing>() {
        override fun toString() = "Exception: $exception"
    }
}

internal val Result<*>.succeeded
    get() = this is Result.Success && data != null