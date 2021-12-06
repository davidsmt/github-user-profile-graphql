package com.githubuserprofile.presentation.di

import com.githubuserprofile.presentation.coroutines.CoroutineContextProvider
import com.githubuserprofile.presentation.coroutines.DefaultCoroutineContextProvider
import dagger.Module
import dagger.Provides

@Module
class CoroutineModule {

    @Provides
    fun provideCoroutineContextProvider(): CoroutineContextProvider {
        return DefaultCoroutineContextProvider()
    }

}