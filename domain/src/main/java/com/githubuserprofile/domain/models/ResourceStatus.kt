package com.githubuserprofile.domain.models

sealed class ResourceStatus<T>(val data: T? = null, val error: Throwable? = null) {

    class Success<T>(data: T?) : ResourceStatus<T>(data)

    class Error<T>(error: Throwable?) : ResourceStatus<T>(error = error)

    class Loading<T> : ResourceStatus<T>()

}