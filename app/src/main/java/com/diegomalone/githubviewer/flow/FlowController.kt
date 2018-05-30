package com.diegomalone.githubviewer.flow

import com.diegomalone.githubviewer.model.GithubRepository

interface FlowController {

    companion object {
        const val REPOSITORY_EXTRA = "repositoryExtra"
    }

    fun redirectToRepositoryDetailsActivity(repository: GithubRepository)

    fun redirectToRepositoryUrl(url: String)
}