package com.githubuserprofile.presentation.mappers

import com.githubuserprofile.domain.models.GitHubRepository
import com.githubuserprofile.domain.models.GitHubRepositoryOwner
import com.githubuserprofile.domain.models.GitHubUserProfile
import com.githubuserprofile.presentation.models.GitHubRepositoryOwnerUiModel
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
                name = it.name,
                description = it.description,
                owner = mapRepositoryOwner(it.owner),
                language = it.language,
                stargazerCount = it.stargazerCount.toString()
            )
        }

    private fun mapRepositoryOwner(owner: GitHubRepositoryOwner): GitHubRepositoryOwnerUiModel =
        GitHubRepositoryOwnerUiModel(
            id = owner.id,
            login = owner.login,
            avatarUrl = owner.avatarUrl
        )

}