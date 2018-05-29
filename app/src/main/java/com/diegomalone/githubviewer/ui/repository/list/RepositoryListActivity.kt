package com.diegomalone.githubviewer.ui.repository.list

import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import com.diegomalone.githubviewer.R
import com.diegomalone.githubviewer.base.BaseActivity
import com.diegomalone.githubviewer.model.GithubRepository
import com.diegomalone.githubviewer.ui.repository.list.adapter.RepositoryListAdapter
import com.diegomalone.githubviewer.util.extension.snack
import com.diegomalone.githubviewer.view.EndlessRecyclerOnScrollListener
import kotlinx.android.synthetic.main.content_repository_list.*
import kotlinx.android.synthetic.main.include_toolbar.*
import javax.inject.Inject

class RepositoryListActivity : BaseActivity(), RepositoryListContract.View {

    @Inject
    lateinit var presenter: RepositoryListContract.Presenter

    private var adapter: RepositoryListAdapter? = null

    private val endlessRecyclerOnScrollListener = object : EndlessRecyclerOnScrollListener() {
        override fun onLoadMore() {
            presenter.loadRepositoryList()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_repository_list)
        setupToolbar(toolbar, false)

        activityComponent.inject(this)
        presenter.attachView(this)

        configureUI()

        presenter.loadRepositoryList(true)
    }

    override fun onDestroy() {
        super.onDestroy()
        presenter.detachView()

        if (!configPersistDelegate.instanceSaved) {
            presenter.destroy()
        }
    }

    private fun configureUI() {
        adapter = RepositoryListAdapter(this) { repository ->
            presenter.selectRepository(repository)
        }

        repositoryListRecyclerView.adapter = adapter
        repositoryListRecyclerView.layoutManager = LinearLayoutManager(this)

        repositoryListRecyclerView.addOnScrollListener(endlessRecyclerOnScrollListener)

        swipeRefreshLayout.setOnRefreshListener {
            presenter.loadRepositoryList(true)
        }
    }

    override fun showLoadingIndicator(loadingVisible: Boolean) {
        swipeRefreshLayout.isRefreshing = loadingVisible
    }

    override fun showUnexpectedError() {
        constraintLayout.snack(R.string.error_unexpected)
    }

    override fun clearRepositoryList() {
        endlessRecyclerOnScrollListener.reset()
        adapter?.clearRepositoryList()
    }

    override fun showRepositoryList(repositoryList: List<GithubRepository>) {
        adapter?.addRepositoryList(repositoryList)
        adapter?.notifyDataSetChanged()
    }
}