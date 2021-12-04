package com.githubuserprofile.domain.models

data class GitHubUserProfile(
    val avatarUrl: String,
    val email: String,
    val followersTotalCount: Int,
    val followingTotalCount: Int,
    val login: String,
    val name: String,
    val pinnedItems: List<GitHubRepository>,
    val starredRepositories: List<GitHubRepository>,
    val topRepositories: List<GitHubRepository>
)