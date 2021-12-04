package com.githubuserprofile.presentation.utils

import com.githubuserprofile.presentation.coroutines.CoroutineContextProvider
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi

class TestCoroutineContextProvider : CoroutineContextProvider {

    @ExperimentalCoroutinesApi
    override fun main(): CoroutineDispatcher = Dispatchers.Unconfined

    @ExperimentalCoroutinesApi
    override fun io(): CoroutineDispatcher = Dispatchers.Unconfined

}
