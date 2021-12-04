package com.githubuserprofile.presentation.models

data class GitHubUserProfileUiModel(
    val avatarUrl: String,
    val email: String,
    val followersTotalCount: String,
    val followingTotalCount: String,
    val login: String,
    val name: String,
    val pinnedItems: List<GitHubRepositoryUiModel>,
    val starredRepositories: List<GitHubRepositoryUiModel>,
    val topRepositories: List<GitHubRepositoryUiModel>
)