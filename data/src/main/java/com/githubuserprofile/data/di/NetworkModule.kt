package com.githubuserprofile.data.di

import com.githubuserprofile.data.network.ApolloManager
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
open class NetworkModule {

    @Singleton
    @Provides
    fun provideApolloManager(): ApolloManager {
        return ApolloManager()
    }

}