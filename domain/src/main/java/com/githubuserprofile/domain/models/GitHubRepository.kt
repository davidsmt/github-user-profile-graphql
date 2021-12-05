package com.githubuserprofile.domain.models

data class GitHubRepository(
    val id: String,
    val name: String,
    val description: String,
    val owner: GitHubRepositoryOwner,
    val language: String,
    val stargazerCount: Int
)