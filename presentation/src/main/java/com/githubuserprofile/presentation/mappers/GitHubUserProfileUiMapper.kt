package com.githubuserprofile.presentation.mappers

import com.githubuserprofile.domain.models.GitHubRepository
import com.githubuserprofile.domain.models.GitHubUserProfile
import com.githubuserprofile.presentation.models.GitHubRepositoryUiModel
import com.githubuserprofile.presentation.models.GitHubUserProfileUiModel

class GitHubUserProfileUiMapper {

    fun map(profile: GitHubUserProfile): GitHubUserProfileUiModel =
        GitHubUserProfileUiModel(
            avatarUrl = profile.avatarUrl,
            email = profile.email,
            followersTotalCount = profile.followersTotalCount.toString(),
            followingTotalCount = profile.followingTotalCount.toString(),
            login = profile.login,
            name = profile.name,
            pinnedItems = mapRepositories(profile.pinnedItems),
            starredRepositories = mapRepositories(profile.starredRepositories),
            topRepositories = mapRepositories(profile.topRepositories),
        )

    private fun mapRepositories(repositories: List<GitHubRepository>) =
        repositories.map {
            GitHubRepositoryUiModel(
                id = it.id,
                name = it.name
            )
        }

}