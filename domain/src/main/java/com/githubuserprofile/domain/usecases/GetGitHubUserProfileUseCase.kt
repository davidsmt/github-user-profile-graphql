package com.githubuserprofile.domain.usecases

import com.githubuserprofile.domain.Repository
import javax.inject.Inject

class GetGitHubUserProfileUseCase @Inject constructor(
    private val repository: Repository
) {
    suspend operator fun invoke(forceRefresh: Boolean = false) = repository.loadUserProfile(forceRefresh)
}