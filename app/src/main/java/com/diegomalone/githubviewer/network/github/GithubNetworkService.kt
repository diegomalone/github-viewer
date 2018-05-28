package com.diegomalone.githubviewer.network.github

import com.diegomalone.githubviewer.model.GithubRepository
import com.diegomalone.githubviewer.network.GithubDataSource
import io.reactivex.Observable

class GithubNetworkService(private val api: GithubApi) : GithubDataSource {

    override fun fetchRepositories(page: Int): Observable<List<GithubRepository>> {
        return api.fetchRepositoryList(page);
    }
}