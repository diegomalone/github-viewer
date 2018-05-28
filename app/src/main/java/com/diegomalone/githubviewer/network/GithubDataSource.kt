package com.diegomalone.githubviewer.network

import com.diegomalone.githubviewer.model.GithubRepository
import io.reactivex.Observable

interface GithubDataSource {

    fun fetchRepositories(page: Int): Observable<List<GithubRepository>>
}