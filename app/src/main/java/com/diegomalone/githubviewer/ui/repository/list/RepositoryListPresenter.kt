package com.diegomalone.githubviewer.ui.repository.list

import com.diegomalone.githubviewer.base.GithubViewerBasePresenter
import com.diegomalone.githubviewer.di.AppComponent
import com.diegomalone.githubviewer.model.GithubRepository
import com.diegomalone.githubviewer.network.GithubDataSource
import com.diegomalone.githubviewer.util.extension.addToCompositeDisposable
import timber.log.Timber

class RepositoryListPresenter(appComponent: AppComponent?,
                              private val githubDataSource: GithubDataSource) : RepositoryListContract.Presenter,
        GithubViewerBasePresenter<RepositoryListContract.View>() {

    init {
        appComponent?.inject(this)
    }

    override fun loadRepositoryList() {
        view?.showLoadingIndicator(true)

        githubDataSource.fetchRepositories(1)
                .observeOn(scheduler.ui)
                .subscribeOn(scheduler.io)
                .doOnTerminate {
                    view?.showLoadingIndicator(false)
                }
                .subscribe({ repositoryList ->
                    processRepositoryList(repositoryList)
                }, { e ->
                    view?.showUnexpectedError()
                    Timber.e(e)
                })
                .addToCompositeDisposable(compositeDisposable)
    }

    override fun selectRepository(repository: GithubRepository) {
        // TODO
    }

    private fun processRepositoryList(repositoryList: List<GithubRepository>) {
        view?.showRepositoryList(repositoryList)
    }
}