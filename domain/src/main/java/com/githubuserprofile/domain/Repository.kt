package com.githubuserprofile.domain

import com.githubuserprofile.domain.models.GitHubUserProfile
import com.githubuserprofile.domain.models.ResourceStatus
import kotlinx.coroutines.flow.Flow

interface Repository {

    suspend fun loadUserProfile(forceRefresh: Boolean): Flow<ResourceStatus<GitHubUserProfile>>

}