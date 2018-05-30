package com.diegomalone.githubviewer.di

import com.diegomalone.githubviewer.di.module.ActivityModule
import com.diegomalone.githubviewer.ui.repository.detail.RepositoryDetailsActivity
import com.diegomalone.githubviewer.ui.repository.list.RepositoryListActivity
import dagger.Subcomponent

@PerActivity
@Subcomponent(modules = [ActivityModule::class])
interface ActivityComponent {

    fun inject(repositoryListActivity: RepositoryListActivity)
    fun inject(repositoryDetailsActivity: RepositoryDetailsActivity)
}