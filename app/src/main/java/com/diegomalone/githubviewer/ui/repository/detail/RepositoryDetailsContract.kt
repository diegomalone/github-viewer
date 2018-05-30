package com.diegomalone.githubviewer.ui.repository.detail

import com.diegomalone.githubviewer.base.BasePresenter
import com.diegomalone.githubviewer.base.BaseView
import com.diegomalone.githubviewer.model.GithubPullRequest
import com.diegomalone.githubviewer.model.GithubRepository

interface RepositoryDetailsContract {

    interface View : BaseView {
        fun setTitle(title: String)
        fun showPullRequestList(pullRequestList: List<GithubPullRequest>)
    }

    interface Presenter : BasePresenter<View> {
        fun setupIntentParameters(repository: GithubRepository)

        fun isListLoaded(): Boolean

        fun loadPullRequestList()

        fun selectPullRequest(pullRequest: GithubPullRequest)
    }
}