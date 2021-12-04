package com.githubuserprofile.data.di

import android.content.Context

interface DataApplication {

    fun getDataComponent(): DataComponent

    companion object {

        fun getDataComponent(context: Context): DataComponent {
            return (context.applicationContext as DataApplication).getDataComponent()
        }
    }

}