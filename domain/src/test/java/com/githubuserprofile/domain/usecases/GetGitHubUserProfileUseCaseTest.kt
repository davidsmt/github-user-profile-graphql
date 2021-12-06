package com.githubuserprofile.domain.usecases

import com.githubuserprofile.domain.Repository
import com.githubuserprofile.domain.models.GitHubUserProfile
import com.githubuserprofile.domain.models.ResourceStatus
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runBlockingTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.Test

@ExperimentalCoroutinesApi
class GetGitHubUserProfileUseCaseTest {

    private lateinit var repository: Repository
    private lateinit var useCase: GetGitHubUserProfileUseCase

    private val coroutineDispatcher = TestCoroutineDispatcher()

    @Before
    fun setUp() {
        Dispatchers.setMain(Dispatchers.Unconfined)

        repository = mockk()

        useCase = GetGitHubUserProfileUseCase(repository)
    }

    @Test
    fun `when use case is executed then repository loadUserProfile should be called`() =
        coroutineDispatcher.runBlockingTest {
            val forceRefresh = false
            val gitHubUserProfile = buildGitHubUserProfile()
            val flow = flow {
                emit(ResourceStatus.Loading())
                emit(ResourceStatus.Success(gitHubUserProfile))
            }

            coEvery {
                repository.loadUserProfile(forceRefresh)
            } returns flow

            useCase.invoke(forceRefresh)

            coVerify {
                repository.loadUserProfile(forceRefresh)
            }
        }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    private fun buildGitHubUserProfile() = GitHubUserProfile(
        avatarUrl = "avatarUrl",
        email = "email",
        followersTotalCount = 1,
        followingTotalCount = 2,
        login = "login",
        name = "name",
        pinnedItems = emptyList(),
        starredRepositories = emptyList(),
        topRepositories = emptyList()
    )

}
