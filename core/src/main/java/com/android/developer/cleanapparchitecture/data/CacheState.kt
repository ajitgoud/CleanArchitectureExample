package com.android.developer.cleanapparchitecture.data

sealed class CacheState<T> {

    class Loading<T> : CacheState<T>()
    data class Success<T>(val data: T) : CacheState<T>()
    data class Failed<T>(val message: String) : CacheState<T>()

    companion object {
        fun <T> loading() = Loading<T>()
        fun <T> success(data: T) = Success(data)
        fun <T> failed(message: String) = Failed<T>(message)
    }

}