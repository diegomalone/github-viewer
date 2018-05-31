package com.diegomalone.githubviewer.model

object GithubRepositoryFactory {

    val testRepository by lazy {
        GithubRepository("Some github repository", "Description of the test repository",
                1000, 350, GithubUserFactory.testUser)
    }

    val testRepositoryAlternative by lazy {
        GithubRepository("Another test repository", "Description of the second test repository",
                389, 93, GithubUserFactory.secondUser)
    }

    val testRepositoryThird by lazy {
        GithubRepository("Third test repository", "Description of the third test repository",
                230, 45, GithubUserFactory.secondUser)
    }

    val repositoryList = arrayListOf(testRepository, testRepositoryAlternative)
    val secondRepositoryList = arrayListOf(testRepositoryThird)

}