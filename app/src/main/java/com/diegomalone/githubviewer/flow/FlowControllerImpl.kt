package com.diegomalone.githubviewer.flow

import android.app.Activity
import android.content.Intent
import android.net.Uri
import com.diegomalone.githubviewer.flow.FlowController.Companion.REPOSITORY_EXTRA
import com.diegomalone.githubviewer.model.GithubRepository
import com.diegomalone.githubviewer.ui.repository.detail.RepositoryDetailsActivity
import java.lang.ref.WeakReference

class FlowControllerImpl(private val activity: WeakReference<Activity>?) : FlowController {

    override fun redirectToRepositoryDetailsActivity(repository: GithubRepository) {
        activity?.get()?.let {
            val intent = Intent(it, RepositoryDetailsActivity::class.java)
            intent.putExtra(REPOSITORY_EXTRA, repository)
            it.startActivity(intent)
        }
    }

    override fun redirectToRepositoryUrl(url: String) {
        activity?.get()?.let {
            val browserIntent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
            it.startActivity(browserIntent)
        }
    }
}