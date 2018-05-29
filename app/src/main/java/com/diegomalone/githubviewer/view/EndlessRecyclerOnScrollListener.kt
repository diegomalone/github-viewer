package com.diegomalone.githubviewer.view

import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView


abstract class EndlessRecyclerOnScrollListener : RecyclerView.OnScrollListener() {

    companion object {
        private const val VISIBLE_THRESHOLD = 5
    }

    private var previousTotal = 0
    private var isLoading = true

    override fun onScrolled(recyclerView: RecyclerView?, dx: Int, dy: Int) {
        super.onScrolled(recyclerView, dx, dy)

        recyclerView?.let {
            val layoutManager = (it.layoutManager as? LinearLayoutManager) ?: return

            val visibleItemCount = it.childCount
            val totalItemCount = it.layoutManager.itemCount
            val firstVisibleItem = layoutManager.findFirstVisibleItemPosition()

            if (isLoading) {
                if (totalItemCount > previousTotal) {
                    isLoading = false
                    previousTotal = totalItemCount
                }
            }

            val visibleThreshold = VISIBLE_THRESHOLD
            if (!isLoading &&
                    (totalItemCount - visibleItemCount <= firstVisibleItem + visibleThreshold)) {
                isLoading = true
                onLoadMore()
            }
        }
    }

    abstract fun onLoadMore()

    fun reset() {
        isLoading = true
        previousTotal = 0
    }
}