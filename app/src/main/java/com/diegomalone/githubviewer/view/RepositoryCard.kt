package com.diegomalone.githubviewer.view

import android.content.Context
import android.support.v7.widget.CardView
import android.util.AttributeSet
import android.view.LayoutInflater
import com.bumptech.glide.request.RequestOptions
import com.diegomalone.githubviewer.R
import com.diegomalone.githubviewer.base.GlideApp
import com.diegomalone.githubviewer.model.GithubRepository
import kotlinx.android.synthetic.main.view_repository_card.view.*

class RepositoryCard : CardView {

    var repository: GithubRepository? = null
        set(value) {
            field = value
            updateRepository()
        }

    constructor(context: Context) : this(context, null)
    constructor(context: Context, attrs: AttributeSet?) : this(context, attrs, 0)

    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {
        LayoutInflater.from(context).inflate(R.layout.view_repository_card, this, true)

        radius = context.resources.getDimension(R.dimen.default_card_corner_radius)
        cardElevation = context.resources.getDimension(R.dimen.default_card_elevation)
    }

    private fun updateRepository() {
        repositoryName.text = repository?.name
        repositoryDetails.text = repository?.description
        userLogin.text = repository?.owner?.login

        forkCount.text = repository?.totalForks?.toString()
        starCount.text = repository?.totalStars?.toString()

        GlideApp.with(context)
                .load(repository?.owner?.avatarUrl)
                .apply(RequestOptions.circleCropTransform())
                .error(R.drawable.ic_user_no_photo)
                .into(repositoryOwnerImage)
    }
}