package com.githubuserprofile.app

import android.app.Application
import com.githubuserprofile.app.di.AppComponent
import com.githubuserprofile.app.di.DaggerAppComponent
import com.githubuserprofile.view.di.ViewComponent
import com.githubuserprofile.view.di.ViewComponentFactoryProvider

class GitHubUserProfileApplication : Application(), ViewComponentFactoryProvider {

    // DI components
    private val _appComponent: AppComponent by lazy {
        DaggerAppComponent.factory().create()
    }

    override fun provideViewComponentFactory(): ViewComponent.Factory =
        _appComponent.viewComponentFactory()

}
