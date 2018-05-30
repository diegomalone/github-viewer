package com.diegomalone.githubviewer.ui.repository.list

import com.diegomalone.githubviewer.base.BasePresenter
import com.diegomalone.githubviewer.base.BaseView
import com.diegomalone.githubviewer.model.GithubRepository

interface RepositoryListContract {

    interface View : BaseView {
        fun clearRepositoryList()

        fun showRepositoryList(repositoryList: List<GithubRepository>)
    }

    interface Presenter : BasePresenter<View> {
        fun isListLoaded(): Boolean

        fun loadRepositoryList(firstPage: Boolean = false)

        fun selectRepository(repository: GithubRepository)
    }
}