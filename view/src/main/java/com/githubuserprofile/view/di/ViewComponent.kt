package com.githubuserprofile.view.di

import com.githubuserprofile.data.di.DataComponent
import com.githubuserprofile.view.screens.githubuserprofile.GitHubUserProfileFragment
import dagger.Component

@Component(
    dependencies = [DataComponent::class],
    modules = [
        PresentationViewModelModule::class
    ]
)
@Fragment
interface ViewComponent {

    @Component.Factory
    interface Factory {
        fun create(dataComponent: DataComponent): ViewComponent
    }

    fun inject(fragment: GitHubUserProfileFragment)

}