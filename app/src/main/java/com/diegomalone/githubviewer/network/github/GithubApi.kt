package com.diegomalone.githubviewer.network.github

import com.diegomalone.githubviewer.model.GithubRepository
import io.reactivex.Observable
import retrofit2.http.POST
import retrofit2.http.Query

interface GithubApi {

    @POST("search/repositories")
    fun fetchRepositoryList(@Query("page") page: Int): Observable<List<GithubRepository>>
}