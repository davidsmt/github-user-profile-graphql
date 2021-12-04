package com.githubuserprofile.data.di

import com.githubuserprofile.data.network.ApolloManager
import com.githubuserprofile.data.respository.RepositoryImpl
import com.githubuserprofile.data.respository.datasources.remote.RemoteDataSource
import com.githubuserprofile.domain.Repository
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
open class RepositoryModule {

    @Singleton
    @Provides
    fun provideRepository(
        remoteDataSource: RemoteDataSource
    ): Repository {
        return RepositoryImpl(remoteDataSource)
    }

    @Singleton
    @Provides
    fun provideRemoteDataSource(apolloManager: ApolloManager): RemoteDataSource {
        return RemoteDataSource(apolloManager)
    }

}