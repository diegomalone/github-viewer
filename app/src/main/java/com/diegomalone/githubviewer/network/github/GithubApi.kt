package com.diegomalone.githubviewer.network.github

import com.diegomalone.githubviewer.model.GithubPullRequest
import com.diegomalone.githubviewer.model.GithubRepository
import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface GithubApi {

    @GET("search/repositories")
    fun fetchRepositoryList(@Query("q") queryString: String,
                            @Query("sort") sortType: String,
                            @Query("page") page: Int): Observable<GithubResponse<List<GithubRepository>>>

    @GET("repos/{user}/{repository}/pulls")
    fun fetchPullRequestList(@Path("user") user: String?,
                             @Path("repository") repository: String?): Observable<List<GithubPullRequest>>
}