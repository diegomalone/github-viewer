package com.diegomalone.githubviewer.network

import com.diegomalone.githubviewer.model.GithubPullRequest
import com.diegomalone.githubviewer.model.GithubRepository
import com.diegomalone.githubviewer.model.GithubUser
import io.reactivex.Observable

interface GithubDataSource {

    fun fetchJavaRepositories(page: Int): Observable<List<GithubRepository>>

    fun fetchPullRequests(user: GithubUser?, repository: GithubRepository?): Observable<List<GithubPullRequest>>
}