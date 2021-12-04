package com.githubuserprofile.app

import android.app.Application
import com.githubuserprofile.data.di.DaggerDataComponent
import com.githubuserprofile.data.di.DataApplication
import com.githubuserprofile.data.di.DataComponent
import com.githubuserprofile.view.di.DaggerViewComponent
import com.githubuserprofile.view.di.ViewApplication
import com.githubuserprofile.view.di.ViewComponent

class GitHubUserProfileApplication : Application(), DataApplication, ViewApplication {

    // DI components
    private val _dataComponent: DataComponent by lazy {
        DaggerDataComponent.factory().create()
    }

    private val _viewComponent: ViewComponent by lazy {
        DaggerViewComponent.factory().create(_dataComponent)
    }

    override fun getDataComponent(): DataComponent = _dataComponent
    override fun getViewComponent(): ViewComponent = _viewComponent
}
