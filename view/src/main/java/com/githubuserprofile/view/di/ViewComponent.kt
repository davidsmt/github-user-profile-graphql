package com.githubuserprofile.view.di

import com.githubuserprofile.view.screens.githubuserprofile.GitHubUserProfileFragment
import dagger.Subcomponent

@Subcomponent
@FragmentScope
interface ViewComponent {

    @Subcomponent.Factory
    interface Factory {
        fun create(): ViewComponent
    }

    fun inject(fragment: GitHubUserProfileFragment)

}

interface ViewComponentFactoryProvider {
    fun provideViewComponentFactory(): ViewComponent.Factory
}
