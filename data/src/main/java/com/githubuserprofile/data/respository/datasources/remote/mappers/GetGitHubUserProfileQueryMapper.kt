package com.githubuserprofile.data.respository.datasources.remote.mappers

import GetGitHubUserProfileQuery
import com.githubuserprofile.domain.models.GitHubRepository
import com.githubuserprofile.domain.models.GitHubRepositoryOwner
import com.githubuserprofile.domain.models.GitHubUserProfile

class GetGitHubUserProfileQueryMapper {

    fun map(profile: GetGitHubUserProfileQuery.Profile): GitHubUserProfile =
        GitHubUserProfile(
            avatarUrl = (profile.avatarUrl as String?).orEmpty(),
            email = (profile.email as String?).orEmpty(),
            followersTotalCount = profile.followers.totalCount,
            followingTotalCount = profile.following.totalCount,
            login = (profile.login as String?).orEmpty(),
            name = profile.name.orEmpty(),
            pinnedItems = mapPinnedItems(profile.pinnedItems),
            starredRepositories = mapStarredRepositories(profile.starredRepositories),
            topRepositories = mapTopRepositories(profile.topRepositories),
        )

    private fun mapPinnedItems(pinnedItems: GetGitHubUserProfileQuery.PinnedItems) =
        pinnedItems.nodes?.map {
            GitHubRepository(
                id = it?.asRepository?.id.orEmpty(),
                name = it?.asRepository?.name.orEmpty(),
                description = it?.asRepository?.description.orEmpty(),
                owner = mapGitHubRepositoryOwner(
                    it?.asRepository?.owner?.id,
                    it?.asRepository?.owner?.login,
                    it?.asRepository?.owner?.avatarUrl
                ),
                language = it?.asRepository?.languages?.nodes?.firstOrNull()?.name.orEmpty(),
                stargazerCount = it?.asRepository?.stargazerCount ?: 0
            )
        }.orEmpty()

    private fun mapStarredRepositories(starredRepositories: GetGitHubUserProfileQuery.StarredRepositories) =
        starredRepositories.nodes?.map {
            GitHubRepository(
                id = it?.id.orEmpty(),
                name = it?.name.orEmpty(),
                description = it?.description.orEmpty(),
                owner = mapGitHubRepositoryOwner(
                    it?.owner?.id,
                    it?.owner?.login,
                    it?.owner?.avatarUrl
                ),
                language = it?.languages?.nodes?.firstOrNull()?.name.orEmpty(),
                stargazerCount = it?.stargazerCount ?: 0
            )
        }.orEmpty()

    private fun mapTopRepositories(topRepositories: GetGitHubUserProfileQuery.TopRepositories) =
        topRepositories.nodes?.map {
            GitHubRepository(
                id = it?.id.orEmpty(),
                name = it?.name.orEmpty(),
                description = it?.description.orEmpty(),
                owner = mapGitHubRepositoryOwner(
                    it?.owner?.id,
                    it?.owner?.login,
                    it?.owner?.avatarUrl
                ),
                language = it?.languages?.nodes?.firstOrNull()?.name.orEmpty(),
                stargazerCount = it?.stargazerCount ?: 0
            )
        }.orEmpty()

    private fun mapGitHubRepositoryOwner(
        id: String?,
        login: String?,
        avatarUrl: Any?
    ): GitHubRepositoryOwner =
        GitHubRepositoryOwner(
            id = id.orEmpty(),
            login = login.orEmpty(),
            avatarUrl = (avatarUrl as String?).orEmpty(),
        )
}