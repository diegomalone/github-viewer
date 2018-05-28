package com.diegomalone.githubviewer.di.module

import com.diegomalone.githubviewer.network.GithubDataSource
import com.diegomalone.githubviewer.network.github.GithubApi
import com.diegomalone.githubviewer.network.github.GithubNetworkService
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
open class DataModule {

    @Singleton
    @Provides
    open fun providesGithubDataSource(api: GithubApi): GithubDataSource = GithubNetworkService(api)
}