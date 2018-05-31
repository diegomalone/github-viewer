package com.diegomalone.githubviewer.model

object GithubPullRequestFactory {

    val testPullRequest by lazy {
        GithubPullRequest("Some pull request title", "Description of the test PR",
                "", "2018-05-30T17:04:39Z", GithubUserFactory.testUser)
    }

    val testPullRequestAlternative by lazy {
        GithubPullRequest("Another PR title", "Description of the second test PR",
                "", "2018-05-29T12:34:19Z", GithubUserFactory.secondUser)
    }

    val pullRequestList = arrayListOf(testPullRequest, testPullRequestAlternative)
}