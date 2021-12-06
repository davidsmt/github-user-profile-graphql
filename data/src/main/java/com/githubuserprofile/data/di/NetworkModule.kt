package com.githubuserprofile.data.di

import com.githubuserprofile.data.network.ApolloManager
import dagger.Module
import dagger.Provides

@Module
open class NetworkModule {

    @Provides
    fun provideApolloManager(): ApolloManager {
        return ApolloManager()
    }

}