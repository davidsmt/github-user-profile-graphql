package com.githubuserprofile.presentation.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.githubuserprofile.domain.models.GitHubUserProfile
import com.githubuserprofile.domain.models.ResourceStatus
import com.githubuserprofile.domain.usecases.GetGitHubUserProfileUseCase
import com.githubuserprofile.presentation.coroutines.CoroutineContextProvider
import com.githubuserprofile.presentation.mappers.GitHubUserProfileUiMapper
import com.githubuserprofile.presentation.models.GitHubUserProfileUiModel
import com.githubuserprofile.presentation.models.UiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

class GitHubUserProfileViewModel @Inject constructor(
    private val getGitHubUserProfileUseCase: GetGitHubUserProfileUseCase,
    private val coroutineContextProvider: CoroutineContextProvider
) : ViewModel() {

    private val state: MutableStateFlow<UiState<GitHubUserProfileUiModel>> =
        MutableStateFlow(UiState.Loading())
    val uiState = state.asLiveData()

    private val mapper = GitHubUserProfileUiMapper()

    fun start() {
        viewModelScope.launch(coroutineContextProvider.io()) {
            getGitHubUserProfileUseCase.invoke().collect {
                updateUiState(it)
            }
        }
    }

    private suspend fun updateUiState(resourceStatus: ResourceStatus<GitHubUserProfile>) {
        val newState = when (resourceStatus) {
            is ResourceStatus.Loading -> UiState.Loading()
            is ResourceStatus.Success -> {
                resourceStatus.data?.let {
                    UiState.Loaded(mapper.map(it))
                } ?: UiState.Empty()
            }
            is ResourceStatus.Error -> UiState.Error(resourceStatus.error)
        }
        state.emit(newState)
    }

    fun onRefresh() {
        viewModelScope.launch(coroutineContextProvider.io()) {
            getGitHubUserProfileUseCase(true).collect {
                updateUiState(it)
            }
        }
    }

    fun onPinnedItemClicked() {
        // No-op
    }

    fun onTopRepositoryItemClicked() {
        // No-op
    }

    fun onStarredRepositoryItemClicked() {
        // No-op
    }

}