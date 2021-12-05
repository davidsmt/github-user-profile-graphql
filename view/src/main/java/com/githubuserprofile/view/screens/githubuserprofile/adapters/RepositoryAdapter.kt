package com.githubuserprofile.view.screens.githubuserprofile.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.githubuserprofile.presentation.models.GitHubRepositoryUiModel
import com.githubuserprofile.view.databinding.LayoutRepositoryItemBinding

class RepositoryAdapter(
    private val listener: Listener
) : ListAdapter<GitHubRepositoryUiModel, RepositoryAdapter.GitHubRepositoryHolder>(
    GitHubRepositoryItemCallback()
) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GitHubRepositoryHolder =
        GitHubRepositoryHolder(
            binding = LayoutRepositoryItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            ),
            listener = listener
        )

    override fun onBindViewHolder(holder: GitHubRepositoryHolder, position: Int) {
        holder.bind(getItem(position))
    }

    class GitHubRepositoryHolder(
        private val binding: LayoutRepositoryItemBinding,
        private val listener: Listener
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(uiModel: GitHubRepositoryUiModel) {
            with(binding) {
                Glide
                    .with(binding.root.context)
                    .load(uiModel.owner.avatarUrl)
                    .circleCrop()
                    .into(binding.repositoryUserProfileAvatar)

                repositoryUserProfileLogin.text = uiModel.owner.login

                repositoryTitle.text = uiModel.name
                repositoryDescription.text = uiModel.description

                repositoryFavoritesCounter.text = uiModel.stargazerCount
                repositoryLanguageLabel.text = uiModel.language

                root.setOnClickListener {
                    listener.onItemClicked(adapterPosition)
                }
            }
        }
    }

    class GitHubRepositoryItemCallback : DiffUtil.ItemCallback<GitHubRepositoryUiModel>() {
        override fun areItemsTheSame(
            old: GitHubRepositoryUiModel,
            new: GitHubRepositoryUiModel
        ): Boolean = old.id == new.id

        override fun areContentsTheSame(
            old: GitHubRepositoryUiModel,
            new: GitHubRepositoryUiModel
        ): Boolean = old == new
    }

    interface Listener {
        fun onItemClicked(position: Int)
    }
}

