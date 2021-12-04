package com.githubuserprofile.presentation.mappers

import com.githubuserprofile.domain.models.GitHubRepository
import com.githubuserprofile.domain.models.GitHubUserProfile
import com.githubuserprofile.presentation.models.GitHubRepositoryUiModel
import com.githubuserprofile.presentation.models.GitHubUserProfileUiModel
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

class GitHubUserProfileUiMapperTest {

    private lateinit var mapper: GitHubUserProfileUiMapper

    @Before
    fun setUp() {
        mapper = GitHubUserProfileUiMapper()
    }

    @Test
    fun `when executed then it should return correct mapped ui model`() {
        val gitHubUserProfile = buildGitHubUserProfile()
        val gitHubUserProfileUiModel = buildGitHubUserProfileUiModel()
        val mappedObject = mapper.map(gitHubUserProfile)

        assertEquals(gitHubUserProfileUiModel, mappedObject)
    }

    private fun buildGitHubUserProfile() = GitHubUserProfile(
        avatarUrl = "avatarUrl",
        email = "email",
        followersTotalCount = 1,
        followingTotalCount = 2,
        login = "login",
        name = "name",
        pinnedItems = listOf(GitHubRepository(id = "id", name = "name")),
        starredRepositories = emptyList(),
        topRepositories = emptyList()
    )

    private fun buildGitHubUserProfileUiModel() = GitHubUserProfileUiModel(
        avatarUrl = "avatarUrl",
        email = "email",
        followersTotalCount = "1",
        followingTotalCount = "2",
        login = "login",
        name = "name",
        pinnedItems = listOf(GitHubRepositoryUiModel(id = "id", name = "name")),
        starredRepositories = emptyList(),
        topRepositories = emptyList()
    )

}
