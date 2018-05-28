package com.diegomalone.githubviewer.di.module

import com.diegomalone.githubviewer.base.GithubViewerApplication
import com.diegomalone.githubviewer.network.GithubDataSource
import com.diegomalone.githubviewer.ui.repository.list.RepositoryListContract
import com.diegomalone.githubviewer.ui.repository.list.RepositoryListPresenter
import dagger.Module
import dagger.Provides
import java.lang.ref.WeakReference

@Module
class PresenterModule {

    @Provides
    fun providesRepositoryListContractPresenter(githubViewerApplication: WeakReference<GithubViewerApplication>,
                                                githubDataSource: GithubDataSource):
            RepositoryListContract.Presenter =
            RepositoryListPresenter(githubViewerApplication.get()?.appComponent, githubDataSource)
}