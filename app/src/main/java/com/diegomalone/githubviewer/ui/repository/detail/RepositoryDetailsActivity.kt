package com.diegomalone.githubviewer.ui.repository.detail

import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import com.diegomalone.githubviewer.R
import com.diegomalone.githubviewer.base.BaseActivity
import com.diegomalone.githubviewer.flow.FlowController.Companion.REPOSITORY_EXTRA
import com.diegomalone.githubviewer.model.GithubPullRequest
import com.diegomalone.githubviewer.ui.repository.detail.adapter.PullRequestListAdapter
import kotlinx.android.synthetic.main.content_repository_details.*
import kotlinx.android.synthetic.main.include_toolbar.*
import javax.inject.Inject

class RepositoryDetailsActivity : BaseActivity(), RepositoryDetailsContract.View {

    @Inject
    lateinit var presenter: RepositoryDetailsContract.Presenter

    private var adapter: PullRequestListAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_repository_details)
        setupToolbar(toolbar, true)

        activityComponent.inject(this)
        presenter.attachView(this)

        configureUI()

        presenter.setupIntentParameters(intent.getParcelableExtra(REPOSITORY_EXTRA))

        presenter.restoreInstanceState(savedInstanceState)

        if (!presenter.isListLoaded()) {
            presenter.loadPullRequestList()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        presenter.detachView()

        if (!configPersistDelegate.instanceSaved) {
            presenter.destroy()
        }
    }

    override fun onSaveInstanceState(outState: Bundle?) {
        super.onSaveInstanceState(outState)
        presenter.saveInstanceState(outState)
    }

    private fun configureUI() {
        adapter = PullRequestListAdapter(this) { pullRequest ->
            presenter.selectPullRequest(pullRequest)
        }

        pullRequestListRecyclerView.adapter = adapter
        pullRequestListRecyclerView.layoutManager = LinearLayoutManager(this)

        swipeRefreshLayout.setOnRefreshListener {
            presenter.loadPullRequestList()
        }
    }

    override fun showLoadingIndicator(loadingVisible: Boolean) {
        swipeRefreshLayout.isRefreshing = loadingVisible
    }

    override fun setTitle(title: String) {
        supportActionBar?.title = title
    }

    override fun showPullRequestList(pullRequestList: List<GithubPullRequest>) {
        adapter?.setPullRequestList(pullRequestList)
        adapter?.notifyDataSetChanged()
    }

}