package com.diegomalone.githubviewer.ui.repository.list

import android.os.Bundle
import com.diegomalone.githubviewer.R
import com.diegomalone.githubviewer.base.BaseActivity
import com.diegomalone.githubviewer.model.GithubRepository
import kotlinx.android.synthetic.main.include_toolbar.*
import javax.inject.Inject

class RepositoryListActivity : BaseActivity(), RepositoryListContract.View {

    @Inject
    lateinit var presenter: RepositoryListContract.Presenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_repository_list)
        setupToolbar(toolbar, false)

        activityComponent.inject(this)
        presenter.attachView(this)

        configureUI()
    }

    override fun onDestroy() {
        super.onDestroy()
        presenter.detachView()

        if (!configPersistDelegate.instanceSaved) {
            presenter.destroy()
        }
    }

    private fun configureUI() {

    }

    override fun showRepositoryList(repositoryList: List<GithubRepository>) {
        TODO("not implemented")
    }
}