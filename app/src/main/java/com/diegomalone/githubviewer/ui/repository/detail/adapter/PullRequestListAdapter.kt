package com.diegomalone.githubviewer.ui.repository.detail.adapter

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.ViewGroup
import com.diegomalone.githubviewer.model.GithubPullRequest
import com.diegomalone.githubviewer.view.ListTitleCard
import com.diegomalone.githubviewer.view.PullRequestCard

class PullRequestListAdapter(private val context: Context,
                             private val listener: (GithubPullRequest) -> Unit) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    companion object {
        const val TOTAL_ITEMS_BEFORE_LIST = 1

        const val TITLE_TYPE = 1
        const val PULL_REQUEST_TYPE = 2
    }

    private var pullRequestList: ArrayList<GithubPullRequest> = ArrayList()
    private var listTitle: String? = null

    fun setPullRequestList(pullRequestList: List<GithubPullRequest>) {
        this.pullRequestList.clear()
        this.pullRequestList.addAll(pullRequestList)
    }

    fun setListTitle(title: String?) {
        this.listTitle = title
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val viewType = getItemViewType(position)

        when (viewType) {
            PULL_REQUEST_TYPE -> {
                val pullRequest = pullRequestList[position - TOTAL_ITEMS_BEFORE_LIST]
                (holder as? PullRequestViewHolder)?.bind(pullRequest, isLastItem(position), listener)
            }
            TITLE_TYPE -> (holder as? TitleViewHolder)?.bind(listTitle)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val layoutParams = RecyclerView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT)

        return if (viewType == PULL_REQUEST_TYPE) {
            val pullRequestCard = PullRequestCard(context)
            pullRequestCard.layoutParams = layoutParams

            PullRequestViewHolder(pullRequestCard)
        } else {
            val listTitleCard = ListTitleCard(context)
            listTitleCard.layoutParams = layoutParams

            TitleViewHolder(listTitleCard)
        }
    }

    override fun getItemCount(): Int {
        return pullRequestList.size + TOTAL_ITEMS_BEFORE_LIST
    }

    override fun getItemViewType(position: Int): Int {
        return if (position == 0) TITLE_TYPE else PULL_REQUEST_TYPE
    }

    private fun isLastItem(position: Int): Boolean {
        if ((position - TOTAL_ITEMS_BEFORE_LIST) == (pullRequestList.size - 1)) {
            return true
        }

        return false
    }

    class PullRequestViewHolder(itemView: PullRequestCard) : RecyclerView.ViewHolder(itemView) {
        fun bind(pullRequest: GithubPullRequest, lastItem: Boolean,
                 listener: (GithubPullRequest) -> Unit) = with((itemView as PullRequestCard)) {
            itemView.setOnClickListener { listener(pullRequest) }

            itemView.pullRequest = pullRequest
            itemView.setDividerVisibility(!lastItem)
        }
    }

    class TitleViewHolder(itemView: ListTitleCard) : RecyclerView.ViewHolder(itemView) {
        fun bind(title: String?) = with((itemView as ListTitleCard)) {
            itemView.title = title
        }
    }
}