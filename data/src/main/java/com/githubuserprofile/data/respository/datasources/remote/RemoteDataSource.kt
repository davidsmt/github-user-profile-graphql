package com.githubuserprofile.data.respository.datasources.remote

import GetGitHubUserProfileQuery
import com.apollographql.apollo.coroutines.await
import com.apollographql.apollo.fetcher.ApolloResponseFetchers
import com.githubuserprofile.data.network.ApolloManager
import com.githubuserprofile.data.respository.datasources.remote.mappers.GetGitHubUserProfileQueryMapper
import com.githubuserprofile.domain.models.GitHubUserProfile
import javax.inject.Inject

class RemoteDataSource @Inject constructor(
    private val apolloManager: ApolloManager
) {

    private val getGitHubUserProfileQueryMapper = GetGitHubUserProfileQueryMapper()

    suspend fun loadUserProfile(forceRefresh: Boolean = false): GitHubUserProfile {
        val cacheStrategy = when (forceRefresh) {
            true -> ApolloResponseFetchers.NETWORK_FIRST
            else -> ApolloResponseFetchers.CACHE_FIRST
        }
        val response = apolloManager.client
            .query(GetGitHubUserProfileQuery(GIT_HUB_USER_PROFILE_TO_LOAD))
            .toBuilder()
            .responseFetcher(cacheStrategy)
            .build()
            .await()

        return getGitHubUserProfileQueryMapper.map(response.data!!.profile!!)
    }

    companion object {
        private const val GIT_HUB_USER_PROFILE_TO_LOAD = "binaryshrey"
    }

}