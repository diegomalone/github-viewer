package com.diegomalone.githubviewer.view

import android.content.Context
import android.support.v7.widget.CardView
import android.util.AttributeSet
import android.view.LayoutInflater
import com.diegomalone.githubviewer.R
import com.diegomalone.githubviewer.model.GithubPullRequest
import kotlinx.android.synthetic.main.view_pull_request_card.view.*

class PullRequestCard : CardView {

    var pullRequest: GithubPullRequest? = null
        set(value) {
            field = value
            updatePullRequest()
        }

    constructor(context: Context) : this(context, null)
    constructor(context: Context, attrs: AttributeSet?) : this(context, attrs, 0)

    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {
        LayoutInflater.from(context).inflate(R.layout.view_pull_request_card, this, true)

        radius = context.resources.getDimension(R.dimen.default_card_corner_radius)
        cardElevation = context.resources.getDimension(R.dimen.default_card_elevation)
    }

    private fun updatePullRequest() {
        // TODO Create layout

        pullRequestTitle.text = pullRequest?.title
    }
}