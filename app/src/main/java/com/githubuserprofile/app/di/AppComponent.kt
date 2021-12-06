package com.githubuserprofile.app.di

import com.githubuserprofile.data.di.NetworkModule
import com.githubuserprofile.data.di.RepositoryModule
import com.githubuserprofile.presentation.di.CoroutineModule
import com.githubuserprofile.presentation.di.PresentationViewModelModule
import com.githubuserprofile.view.di.ViewComponent
import com.githubuserprofile.view.di.ViewModule
import dagger.Component

@Component(
    modules = [
        NetworkModule::class,
        RepositoryModule::class,
        CoroutineModule::class,
        PresentationViewModelModule::class,
        ViewModule::class
    ]
)
@ApplicationScope
interface AppComponent {

    @Component.Factory
    interface Factory {
        fun create(): AppComponent
    }

    fun viewComponentFactory(): ViewComponent.Factory

}