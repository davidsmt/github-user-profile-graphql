package com.githubuserprofile.presentation.viewmodels

import androidx.arch.core.executor.ArchTaskExecutor
import com.githubuserprofile.domain.models.GitHubUserProfile
import com.githubuserprofile.domain.models.ResourceStatus
import com.githubuserprofile.domain.usecases.GetGitHubUserProfileUseCase
import com.githubuserprofile.presentation.models.GitHubUserProfileUiModel
import com.githubuserprofile.presentation.models.UiState
import com.githubuserprofile.presentation.utils.TestCoroutineContextProvider
import com.githubuserprofile.presentation.utils.TestTaskExecutor
import com.githubuserprofile.presentation.utils.TestableObserver
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.runBlockingTest
import kotlinx.coroutines.test.setMain
import org.hamcrest.CoreMatchers.instanceOf
import org.hamcrest.MatcherAssert.assertThat
import org.junit.Before
import org.junit.Test

class GitHubUserProfileViewModelTest {

    private lateinit var getGitHubUserProfileUseCase: GetGitHubUserProfileUseCase
    private lateinit var viewModel: GitHubUserProfileViewModel

    @ExperimentalCoroutinesApi
    private val coroutineDispatcher = TestCoroutineDispatcher()

    @ExperimentalCoroutinesApi
    @Before
    fun setUp() {
        ArchTaskExecutor.getInstance().setDelegate(TestTaskExecutor())
        Dispatchers.setMain(Dispatchers.Unconfined)

        getGitHubUserProfileUseCase = mockk()

        viewModel = GitHubUserProfileViewModel(
            getGitHubUserProfileUseCase,
            TestCoroutineContextProvider()
        )
    }

    @ExperimentalCoroutinesApi
    @Test
    fun `when start and data has not been loaded yet then loading should be shown`() =
        coroutineDispatcher.runBlockingTest {
            //Arrange
            val stateObserver = TestableObserver<UiState<GitHubUserProfileUiModel>>()

            val flow = flow<ResourceStatus<GitHubUserProfile>> {
                emit(ResourceStatus.Loading())
            }

            // When
            coEvery {
                getGitHubUserProfileUseCase.invoke(false)
            } returns flow

            viewModel.uiState.observeForever(stateObserver)
            viewModel.start()

            //Assert
            assertThat(stateObserver.history[0], instanceOf(UiState.Loading::class.java))
        }

    @ExperimentalCoroutinesApi
    @Test
    fun `when start and data has been loaded then content should be shown`() =
        coroutineDispatcher.runBlockingTest {
            //Arrange
            val stateObserver = TestableObserver<UiState<GitHubUserProfileUiModel>>()

            val gitHubUserProfile = buildGitHubUserProfile()
            val flow = flow {
                emit(ResourceStatus.Loading())
                emit(ResourceStatus.Success(gitHubUserProfile))
            }

            // When
            coEvery {
                getGitHubUserProfileUseCase.invoke(false)
            } returns flow

            viewModel.uiState.observeForever(stateObserver)
            viewModel.start()

            //Assert
            assertThat(stateObserver.history[0], instanceOf(UiState.Loading::class.java))
            assertThat(stateObserver.history[1], instanceOf(UiState.Loaded::class.java))
        }

    @ExperimentalCoroutinesApi
    @Test
    fun `when start and data has been loaded and it is null then empty should be shown`() =
        coroutineDispatcher.runBlockingTest {
            //Arrange
            val stateObserver = TestableObserver<UiState<GitHubUserProfileUiModel>>()

            val gitHubUserProfile = null
            val flow = flow<ResourceStatus<GitHubUserProfile>> {
                emit(ResourceStatus.Loading())
                emit(ResourceStatus.Success(gitHubUserProfile))
            }

            // When
            coEvery {
                getGitHubUserProfileUseCase.invoke(false)
            } returns flow

            viewModel.uiState.observeForever(stateObserver)
            viewModel.start()

            //Assert
            assertThat(stateObserver.history[0], instanceOf(UiState.Loading::class.java))
            assertThat(stateObserver.history[1], instanceOf(UiState.Empty::class.java))
        }

    @ExperimentalCoroutinesApi
    @Test
    fun `when start and data has not been loaded yet then error should be shown`() =
        coroutineDispatcher.runBlockingTest {
            //Arrange
            val stateObserver = TestableObserver<UiState<GitHubUserProfileUiModel>>()

            val flow = flow<ResourceStatus<GitHubUserProfile>> {
                emit(ResourceStatus.Loading())
                emit(ResourceStatus.Error(mockk()))
            }

            // When
            coEvery {
                getGitHubUserProfileUseCase.invoke(false)
            } returns flow

            viewModel.uiState.observeForever(stateObserver)
            viewModel.start()

            //Assert
            assertThat(stateObserver.history[0], instanceOf(UiState.Loading::class.java))
            assertThat(stateObserver.history[1], instanceOf(UiState.Error::class.java))
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
