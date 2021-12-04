package com.githubuserprofile.data.respository.datasources.remote.mappers

import GetGitHubUserProfileQuery
import com.githubuserprofile.domain.models.GitHubRepository
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
                name = it?.asRepository?.name.orEmpty()
            )
        }.orEmpty()

    private fun mapStarredRepositories(starredRepositories: GetGitHubUserProfileQuery.StarredRepositories) =
        starredRepositories.nodes?.map {
            GitHubRepository(
                id = it?.id.orEmpty(),
                name = it?.name.orEmpty(),
            )
        }.orEmpty()

    private fun mapTopRepositories(topRepositories: GetGitHubUserProfileQuery.TopRepositories) =
        topRepositories.nodes?.map {
            GitHubRepository(
                id = it?.id.orEmpty(),
                name = it?.name.orEmpty()
            )
        }.orEmpty()

}