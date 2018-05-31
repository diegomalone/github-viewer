package com.diegomalone.githubviewer.presenter

import com.diegomalone.githubviewer.BuildConfig
import com.diegomalone.githubviewer.base.BaseTest
import com.diegomalone.githubviewer.base.GithubViewerApplication
import com.diegomalone.githubviewer.exception.NoNetworkException
import com.diegomalone.githubviewer.model.GithubPullRequestFactory
import com.diegomalone.githubviewer.model.GithubRepositoryFactory
import com.diegomalone.githubviewer.ui.repository.detail.RepositoryDetailsContract
import com.diegomalone.githubviewer.ui.repository.detail.RepositoryDetailsPresenter
import com.nhaarman.mockito_kotlin.*
import io.reactivex.Observable
import junit.framework.Assert.assertFalse
import junit.framework.Assert.assertTrue
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito.mock
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config


@RunWith(RobolectricTestRunner::class)
@Config(constants = BuildConfig::class, application = GithubViewerApplication::class, sdk = [26])
class RepositoryDetailsPresenterTest : BaseTest() {

    @get:Rule
    val mockitoRule = daggerMockRule

    @Mock
    private lateinit var view: RepositoryDetailsContract.View

    private lateinit var repositoryDetailsPresenter: RepositoryDetailsPresenter

    @Before
    fun setup() {
        mockitoRule.initMocks(this)

        repositoryDetailsPresenter = RepositoryDetailsPresenter(appComponent, githubDataSource)
        repositoryDetailsPresenter.attachView(view)
    }

    @Test
    fun test_setupIntentParameters() {
        val testRepository = GithubRepositoryFactory.testRepository

        repositoryDetailsPresenter.setupIntentParameters(testRepository)

        verify(view).setTitle(eq(testRepository.name))
    }

    @Test
    fun test_isListLoaded_loaded() {
        val testPullRequestList = GithubPullRequestFactory.pullRequestList

        whenever(githubDataSource.fetchPullRequests(any(), any())).thenReturn(Observable.just(testPullRequestList))

        repositoryDetailsPresenter.setupIntentParameters(GithubRepositoryFactory.testRepository)
        repositoryDetailsPresenter.loadPullRequestList()

        assertTrue(repositoryDetailsPresenter.isListLoaded())
    }

    @Test
    fun test_isListLoaded_empty() {
        val testPullRequestList = GithubPullRequestFactory.pullRequestList

        whenever(githubDataSource.fetchPullRequests(any(), any())).thenReturn(Observable.just(testPullRequestList))

        assertFalse(repositoryDetailsPresenter.isListLoaded())
    }

    @Test
    fun test_fetchRepository_success() {
        val testPullRequestList = GithubPullRequestFactory.pullRequestList

        whenever(githubDataSource.fetchPullRequests(any(), any())).thenReturn(Observable.just(testPullRequestList))

        repositoryDetailsPresenter.setupIntentParameters(GithubRepositoryFactory.testRepository)
        repositoryDetailsPresenter.loadPullRequestList()

        verify(view).showLoadingIndicator(eq(false))
        verify(view).showPullRequestList(eq(testPullRequestList))
    }

    @Test
    fun test_fetchRepository_noNetwork() {
        val noNetworkException = mock(NoNetworkException::class.java)

        whenever(githubDataSource.fetchPullRequests(any(), any())).thenReturn(Observable.error(noNetworkException))

        repositoryDetailsPresenter.setupIntentParameters(GithubRepositoryFactory.testRepository)
        repositoryDetailsPresenter.loadPullRequestList()

        verify(view).showLoadingIndicator(eq(false))
        verify(view, never()).showPullRequestList(anyOrNull())

        verify(view).showNoNetworkError(anyOrNull())
        verify(view, never()).showUnexpectedError(anyOrNull())
    }

    @Test
    fun test_fetchRepository_serverError() {
        val exception = mock(Exception::class.java)

        whenever(githubDataSource.fetchPullRequests(any(), any())).thenReturn(Observable.error(exception))

        repositoryDetailsPresenter.setupIntentParameters(GithubRepositoryFactory.testRepository)
        repositoryDetailsPresenter.loadPullRequestList()

        verify(view).showLoadingIndicator(eq(false))
        verify(view, never()).showPullRequestList(anyOrNull())

        verify(view).showUnexpectedError(anyOrNull())
        verify(view, never()).showNoNetworkError(anyOrNull())
    }
}