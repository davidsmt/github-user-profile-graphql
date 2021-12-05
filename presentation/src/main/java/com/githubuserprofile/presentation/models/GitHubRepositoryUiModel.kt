package com.githubuserprofile.presentation.models

data class GitHubRepositoryUiModel(
    val id: String,
    val name: String,
    val description: String,
    val owner: GitHubRepositoryOwnerUiModel,
    val language: String,
    val stargazerCount: String
)
