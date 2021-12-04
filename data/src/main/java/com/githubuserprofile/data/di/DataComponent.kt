package com.githubuserprofile.data.di

import com.githubuserprofile.domain.Repository
import dagger.Component
import javax.inject.Singleton

@Component(
    modules = [
        NetworkModule::class,
        RepositoryModule::class
    ]
)
@Singleton
interface DataComponent {

    @Component.Factory
    interface Factory {
        fun create(): DataComponent
    }

    val repository: Repository

}