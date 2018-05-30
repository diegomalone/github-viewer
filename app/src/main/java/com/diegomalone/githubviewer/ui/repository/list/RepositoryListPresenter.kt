package com.diegomalone.githubviewer.ui.repository.list

import android.os.Bundle
import com.diegomalone.githubviewer.base.GithubViewerBasePresenter
import com.diegomalone.githubviewer.di.AppComponent
import com.diegomalone.githubviewer.exception.NoNetworkException
import com.diegomalone.githubviewer.model.GithubRepository
import com.diegomalone.githubviewer.network.GithubDataSource
import com.diegomalone.githubviewer.util.extension.addToCompositeDisposable
import timber.log.Timber

class RepositoryListPresenter(appComponent: AppComponent?,
                              private val githubDataSource: GithubDataSource) : RepositoryListContract.Presenter,
        GithubViewerBasePresenter<RepositoryListContract.View>() {

    companion object {
        private const val INITIAL_PAGE_VALUE = 1

        private const val REPOSITORY_LIST_KEY = "repositoryListKey"
        private const val CURRENT_PAGE_KEY = "currentPageKey"
    }

    init {
        appComponent?.inject(this)
    }

    private val repositoryList = ArrayList<GithubRepository>()
    private var currentPage = INITIAL_PAGE_VALUE

    override fun saveInstanceState(outState: Bundle?) {
        outState?.putParcelableArrayList(REPOSITORY_LIST_KEY, repositoryList)
        outState?.putInt(CURRENT_PAGE_KEY, currentPage)
    }

    override fun restoreInstanceState(savedInstanceState: Bundle?) {
        savedInstanceState?.let {
            currentPage = it.getInt(CURRENT_PAGE_KEY, INITIAL_PAGE_VALUE)
            processRepositoryList(it.getParcelableArrayList(REPOSITORY_LIST_KEY))
        }
    }

    override fun isListLoaded(): Boolean {
        return !repositoryList.isEmpty()
    }

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
                    Timber.e(e)
                    if (e is NoNetworkException) {
                        view?.showNoNetworkError {
                            loadRepositoryList(true)
                        }
                    } else {
                        view?.showUnexpectedError {
                            loadRepositoryList(true)
                        }
                    }
                })
                .addToCompositeDisposable(compositeDisposable)
    }

    override fun selectRepository(repository: GithubRepository) {
        // TODO
    }

    private fun processRepositoryList(repositoryList: List<GithubRepository>) {
        if (currentPage == INITIAL_PAGE_VALUE) {
            this.repositoryList.clear()
            view?.clearRepositoryList()
        }

        this.repositoryList.addAll(repositoryList)
        view?.hideErrorView()
        view?.showRepositoryList(repositoryList)
    }
}