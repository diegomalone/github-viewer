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

    companion object {
        private const val INITIAL_PAGE_VALUE = 1
    }

    init {
        appComponent?.inject(this)
    }

    private var currentPage = INITIAL_PAGE_VALUE

    override fun loadRepositoryList(firstPage: Boolean) {
        view?.showLoadingIndicator(true)

        currentPage = if (firstPage) INITIAL_PAGE_VALUE else (currentPage + 1)

        githubDataSource.fetchJavaRepositories(currentPage)
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
        if (currentPage == INITIAL_PAGE_VALUE) {
            view?.clearRepositoryList()
        }

        view?.showRepositoryList(repositoryList)
    }
}