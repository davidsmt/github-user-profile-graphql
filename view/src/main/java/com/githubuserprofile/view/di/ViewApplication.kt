package com.githubuserprofile.view.di

import android.content.Context

interface ViewApplication {

    fun getViewComponent(): ViewComponent

    companion object {

        fun getViewComponent(context: Context): ViewComponent {
            return (context.applicationContext as ViewApplication).getViewComponent()
        }
    }

}