package com.githubuserprofile.view.screens.githubuserprofile

import android.app.AlertDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.githubuserprofile.presentation.factory.ViewModelFactory
import com.githubuserprofile.presentation.models.GitHubUserProfileUiModel
import com.githubuserprofile.presentation.models.UiState
import com.githubuserprofile.presentation.viewmodels.GitHubUserProfileViewModel
import com.githubuserprofile.view.R
import com.githubuserprofile.view.databinding.FragmentGithubUserProfileFragmentBinding
import com.githubuserprofile.view.di.ViewApplication
import com.githubuserprofile.view.mappers.ErrorsMapper
import com.githubuserprofile.view.screens.githubuserprofile.adapters.RepositoryAdapter
import com.githubuserprofile.view.utils.SpaceItemDecoration
import com.githubuserprofile.view.utils.visible
import javax.inject.Inject


class GitHubUserProfileFragment : Fragment() {

    @Inject
    internal lateinit var viewModelFactory: ViewModelFactory

    private val viewModel: GitHubUserProfileViewModel by viewModels { viewModelFactory }

    private var _binding: FragmentGithubUserProfileFragmentBinding? = null
    private val binding: FragmentGithubUserProfileFragmentBinding
        get() = _binding!!

    private lateinit var pinnedAdapter: RepositoryAdapter
    private lateinit var topRepositoriesAdapter: RepositoryAdapter
    private lateinit var starredRepositoriesAdapter: RepositoryAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        ViewApplication.getViewComponent(requireContext()).inject(this)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentGithubUserProfileFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupViews()
        setupListeners()
        setupObservers()
        viewModel.start()
    }

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }

    private fun setupViews() {
        pinnedAdapter = RepositoryAdapter(listener = object : RepositoryAdapter.Listener {
            override fun onItemClicked(position: Int) {
                viewModel.onPinnedItemClicked()
            }
        })
        topRepositoriesAdapter = RepositoryAdapter(listener = object : RepositoryAdapter.Listener {
            override fun onItemClicked(position: Int) {
                viewModel.onTopRepositoryItemClicked()
            }
        })
        starredRepositoriesAdapter =
            RepositoryAdapter(listener = object : RepositoryAdapter.Listener {
                override fun onItemClicked(position: Int) {
                    viewModel.onStarredRepositoryItemClicked()
                }
            })

        with(binding) {
            pinnedList.addItemDecoration(
                SpaceItemDecoration(resources.getDimensionPixelSize(R.dimen.space_big))
            )
            topRepositoriesList.addItemDecoration(
                SpaceItemDecoration(
                    resources.getDimensionPixelSize(R.dimen.space_big),
                    LinearLayoutManager.HORIZONTAL
                )
            )
            starredRepositoriesList.addItemDecoration(
                SpaceItemDecoration(
                    resources.getDimensionPixelSize(R.dimen.space_big),
                    LinearLayoutManager.HORIZONTAL
                )
            )

            val topRepositoriesLayoutManager = object : LinearLayoutManager(requireContext()) {
                override fun checkLayoutParams(lp: RecyclerView.LayoutParams): Boolean {
                    lp.width = (width * 75) / 100
                    return true
                }
            }
            topRepositoriesLayoutManager.orientation = RecyclerView.HORIZONTAL
            topRepositoriesList.layoutManager = topRepositoriesLayoutManager

            val starredRepositoriesLayoutManager = object : LinearLayoutManager(requireContext()) {
                override fun checkLayoutParams(lp: RecyclerView.LayoutParams): Boolean {
                    lp.width = (width * 75) / 100
                    return true
                }
            }
            starredRepositoriesLayoutManager.orientation = RecyclerView.HORIZONTAL
            starredRepositoriesList.layoutManager = starredRepositoriesLayoutManager

            pinnedList.adapter = pinnedAdapter
            topRepositoriesList.adapter = topRepositoriesAdapter
            starredRepositoriesList.adapter = starredRepositoriesAdapter
        }
    }

    private fun setupListeners() {
        binding.content.setOnRefreshListener {
            viewModel.onRefresh()
        }
    }

    private fun setupObservers() {
        viewModel.uiState.observe(viewLifecycleOwner) { uiState ->
            when (uiState) {
                is UiState.Empty -> showEmptyState()
                is UiState.Error -> showErrorDialog(uiState.error)
                is UiState.Loaded -> showContent(uiState.data)
                is UiState.Loading -> showLoading()
            }
        }
    }

    private fun showEmptyState() {
        with(binding) {
            content.isRefreshing = false

            loading.visible(false)
            content.visible(false)
            emptyContent.visible(true)
        }
    }

    private fun showContent(userProfile: GitHubUserProfileUiModel) {
        with(binding) {
            content.isRefreshing = false

            Glide
                .with(requireContext())
                .load(userProfile.avatarUrl)
                .circleCrop()
                .into(binding.userProfileAvatar)

            userProfileName.text = userProfile.name
            userProfileLogin.text = userProfile.login

            if (userProfile.email.isEmpty()) {
                userProfileEmail.visible(false)
            } else {
                userProfileEmail.text = userProfile.email
                userProfileEmail.visible(true)
            }

            userProfileFollowersCounter.text = userProfile.followersTotalCount
            userProfileFollowingCounter.text = userProfile.followingTotalCount

            pinnedAdapter.submitList(userProfile.pinnedItems)
            topRepositoriesAdapter.submitList(userProfile.pinnedItems)
            starredRepositoriesAdapter.submitList(userProfile.pinnedItems)

            loading.visible(false)
            content.visible(true)
            emptyContent.visible(false)
        }
    }

    private fun showLoading() {
        with(binding) {
            loading.visible(true)
            content.visible(false)
            emptyContent.visible(false)
        }
    }

    private fun showErrorDialog(error: Throwable?) {
        binding.content.isRefreshing = false

        AlertDialog.Builder(context)
            .setMessage(ErrorsMapper(resources).map(error))
            .create()
            .show()
    }

}