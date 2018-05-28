package com.diegomalone.githubviewer.di

import com.diegomalone.githubviewer.di.module.ApiModule
import com.diegomalone.githubviewer.di.module.AppModule
import com.diegomalone.githubviewer.di.module.DataModule
import com.diegomalone.githubviewer.di.module.PresenterModule
import com.diegomalone.githubviewer.network.GithubDataSource
import com.diegomalone.githubviewer.ui.repository.list.RepositoryListPresenter
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [AppModule::class, DataModule::class, ApiModule::class])
interface AppComponent {

    val githubDataSource: GithubDataSource

    operator fun plus(presenterModule: PresenterModule): ConfigPersistentComponent

    fun inject(repositoryListPresenter: RepositoryListPresenter)
}