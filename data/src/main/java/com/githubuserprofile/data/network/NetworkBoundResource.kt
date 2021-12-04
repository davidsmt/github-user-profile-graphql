package com.githubuserprofile.data.network

import com.githubuserprofile.domain.models.ResourceStatus
import kotlinx.coroutines.flow.flow

inline fun <ResultType> networkBoundResource(
    crossinline fetch: suspend () -> ResultType
) = flow<ResourceStatus<ResultType>> {

    emit(ResourceStatus.Loading())

    try {
        emit(ResourceStatus.Success(fetch()))
    } catch (throwable: Throwable) {
        emit(ResourceStatus.Error(throwable))
    }
}