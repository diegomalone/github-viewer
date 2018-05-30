package com.diegomalone.githubviewer.ui.repository.detail

import android.os.Bundle
import com.diegomalone.githubviewer.base.GithubViewerBasePresenter
import com.diegomalone.githubviewer.di.AppComponent
import com.diegomalone.githubviewer.exception.NoNetworkException
import com.diegomalone.githubviewer.model.GithubPullRequest
import com.diegomalone.githubviewer.model.GithubRepository
import com.diegomalone.githubviewer.network.GithubDataSource
import com.diegomalone.githubviewer.util.extension.addToCompositeDisposable
import timber.log.Timber

class RepositoryDetailsPresenter(appComponent: AppComponent?,
                                 private val githubDataSource: GithubDataSource) : RepositoryDetailsContract.Presenter,
        GithubViewerBasePresenter<RepositoryDetailsContract.View>() {

    companion object {
        private const val PULL_REQUEST_LIST_KEY = "repositoryListKey"
    }

    init {
        appComponent?.inject(this)
    }

    private var repository: GithubRepository? = null
    private val pullRequestList = ArrayList<GithubPullRequest>()

    override fun saveInstanceState(outState: Bundle?) {
        outState?.putParcelableArrayList(PULL_REQUEST_LIST_KEY, ArrayList(pullRequestList))
    }

    override fun restoreInstanceState(savedInstanceState: Bundle?) {
        savedInstanceState?.let {
            processPullRequestList(it.getParcelableArrayList(PULL_REQUEST_LIST_KEY))
        }
    }

    override fun setupIntentParameters(repository: GithubRepository) {
        this.repository = repository
        view?.setTitle(repository.name)
    }

    override fun isListLoaded(): Boolean {
        return !pullRequestList.isEmpty()
    }

    override fun loadPullRequestList() {
        view?.showLoadingIndicator(true)

        githubDataSource.fetchPullRequests(repository?.owner, repository)
                .observeOn(scheduler.ui)
                .subscribeOn(scheduler.io)
                .doOnTerminate {
                    view?.showLoadingIndicator(false)
                }
                .subscribe({ pullRequestList ->
                    processPullRequestList(pullRequestList)
                }, { e ->
                    Timber.e(e)
                    if (e is NoNetworkException) {
                        view?.showNoNetworkError {
                            loadPullRequestList()
                        }
                    } else {
                        view?.showUnexpectedError {
                            loadPullRequestList()
                        }
                    }
                })
                .addToCompositeDisposable(compositeDisposable)
    }

    override fun selectPullRequest(pullRequest: GithubPullRequest) {
        flowController?.redirectToRepositoryUrl(pullRequest.url)
    }

    private fun processPullRequestList(pullRequestList: List<GithubPullRequest>) {
        this.pullRequestList.clear()
        this.pullRequestList.addAll(pullRequestList)

        view?.hideErrorView()
        view?.showPullRequestList(pullRequestList)
    }
}