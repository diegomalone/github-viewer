package com.diegomalone.githubviewer.ui.repository.detail.adapter

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.ViewGroup
import com.diegomalone.githubviewer.model.GithubPullRequest
import com.diegomalone.githubviewer.view.PullRequestCard

class PullRequestListAdapter(private val context: Context,
                             private val listener: (GithubPullRequest) -> Unit) : RecyclerView.Adapter<PullRequestListAdapter.ViewHolder>() {

    private var pullRequestList: ArrayList<GithubPullRequest> = ArrayList()

    fun setPullRequestList(pullRequestList: List<GithubPullRequest>) {
        this.pullRequestList.clear()
        this.pullRequestList.addAll(pullRequestList)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(pullRequestList[position], listener)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val pullRequestCard = PullRequestCard(context)
        val layoutParams = RecyclerView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT)
        pullRequestCard.layoutParams = layoutParams

        return ViewHolder(pullRequestCard)
    }

    override fun getItemCount(): Int {
        return pullRequestList.size
    }

    class ViewHolder(itemView: PullRequestCard) : RecyclerView.ViewHolder(itemView) {
        fun bind(pullRequest: GithubPullRequest, listener: (GithubPullRequest) -> Unit) = with((itemView as PullRequestCard)) {
            itemView.setOnClickListener { listener(pullRequest) }

            itemView.pullRequest = pullRequest
        }
    }
}