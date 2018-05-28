package com.diegomalone.githubviewer.ui.repository.list

import com.diegomalone.githubviewer.base.BasePresenter
import com.diegomalone.githubviewer.base.BaseView
import com.diegomalone.githubviewer.model.GithubRepository

interface RepositoryListContract {

    interface View : BaseView {
        fun showRepositoryList(repositoryList: List<GithubRepository>)
    }

    interface Presenter : BasePresenter<View> {
        fun loadRepositoryList()

        fun selectRepository(repository: GithubRepository)
    }
}