package com.diegomalone.githubviewer.ui.repository.list.adapter

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.ViewGroup
import com.diegomalone.githubviewer.model.GithubRepository
import com.diegomalone.githubviewer.view.RepositoryCard

class RepositoryListAdapter(private val context: Context,
                            private val listener: (GithubRepository) -> Unit) : RecyclerView.Adapter<RepositoryListAdapter.ViewHolder>() {

    private var repositoryList: ArrayList<GithubRepository> = ArrayList()

    fun clearRepositoryList() {
        repositoryList.clear()
    }

    fun addRepositoryList(repositoryList: List<GithubRepository>) {
        this.repositoryList.addAll(repositoryList)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(repositoryList[position], listener)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val repositoryCard = RepositoryCard(context)
        val layoutParams = RecyclerView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT)
        repositoryCard.layoutParams = layoutParams

        return ViewHolder(repositoryCard)
    }

    override fun getItemCount(): Int {
        return repositoryList.size
    }

    class ViewHolder(itemView: RepositoryCard) : RecyclerView.ViewHolder(itemView) {
        fun bind(repository: GithubRepository, listener: (GithubRepository) -> Unit) = with((itemView as RepositoryCard)) {
            itemView.setOnClickListener { listener(repository) }

            itemView.repository = repository
        }
    }
}