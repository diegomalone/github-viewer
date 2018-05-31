package com.diegomalone.githubviewer.view

import android.content.Context
import android.support.v7.widget.CardView
import android.util.AttributeSet
import android.view.LayoutInflater
import com.bumptech.glide.request.RequestOptions
import com.diegomalone.githubviewer.R
import com.diegomalone.githubviewer.base.GlideApp
import com.diegomalone.githubviewer.model.GithubPullRequest
import com.diegomalone.githubviewer.util.DateUtils
import com.diegomalone.githubviewer.util.extension.visible
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

    fun setDividerVisibility(visible: Boolean) {
        divider.visible(visible)
    }

    private fun updatePullRequest() {
        pullRequestTitle.text = pullRequest?.title
        pullRequestBody.text = pullRequest?.body

        userLogin.text = pullRequest?.user?.login

        pullRequestDate.text = DateUtils.getUserDate(DateUtils.getDateFromString(pullRequest?.createdAt))

        GlideApp.with(context)
                .load(pullRequest?.user?.avatarUrl)
                .apply(RequestOptions.circleCropTransform())
                .error(R.drawable.ic_user_no_photo)
                .into(pullRequestUserImage)
    }
}