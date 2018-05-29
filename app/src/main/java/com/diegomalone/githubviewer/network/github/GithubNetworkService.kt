package com.diegomalone.githubviewer.network.github

import com.diegomalone.githubviewer.model.GithubRepository
import com.diegomalone.githubviewer.network.GithubDataSource
import io.reactivex.Observable

class GithubNetworkService(private val api: GithubApi) : GithubDataSource {

    companion object {
        const val SORT_TYPE_START = "start"
        const val REPOSITORY_TYPE_JAVA = "language:Java"
    }

    override fun fetchJavaRepositories(page: Int): Observable<List<GithubRepository>> {
        return api.fetchRepositoryList(REPOSITORY_TYPE_JAVA, SORT_TYPE_START, page)
                .flatMap { t: GithubResponse<List<GithubRepository>> ->
                    Observable.just(t.items)
                }
    }
}