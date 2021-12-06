package com.githubuserprofile.data.di

import com.githubuserprofile.data.network.ApolloManager
import com.githubuserprofile.data.respository.RepositoryImpl
import com.githubuserprofile.data.respository.datasources.remote.RemoteDataSource
import com.githubuserprofile.domain.Repository
import dagger.Module
import dagger.Provides

@Module
open class RepositoryModule {

    @Provides
    fun provideRepository(
        remoteDataSource: RemoteDataSource
    ): Repository {
        return RepositoryImpl(remoteDataSource)
    }

    @Provides
    fun provideRemoteDataSource(apolloManager: ApolloManager): RemoteDataSource {
        return RemoteDataSource(apolloManager)
    }

}