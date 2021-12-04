package com.githubuserprofile.data.respository

import com.githubuserprofile.data.network.networkBoundResource
import com.githubuserprofile.data.respository.datasources.remote.RemoteDataSource
import com.githubuserprofile.domain.Repository
import com.githubuserprofile.domain.models.GitHubUserProfile
import com.githubuserprofile.domain.models.ResourceStatus
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject


class RepositoryImpl @Inject constructor(
    private val remoteDataSource: RemoteDataSource
) : Repository {

    override suspend fun loadUserProfile(forceRefresh: Boolean): Flow<ResourceStatus<GitHubUserProfile>> =
        networkBoundResource(
            fetch = { remoteDataSource.loadUserProfile(forceRefresh) }
        )

}