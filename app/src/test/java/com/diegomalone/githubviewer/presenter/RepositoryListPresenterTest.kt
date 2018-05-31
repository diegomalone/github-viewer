package com.diegomalone.githubviewer.presenter

import com.diegomalone.githubviewer.BuildConfig
import com.diegomalone.githubviewer.base.BaseTest
import com.diegomalone.githubviewer.base.GithubViewerApplication
import com.diegomalone.githubviewer.exception.NoNetworkException
import com.diegomalone.githubviewer.model.GithubRepositoryFactory
import com.diegomalone.githubviewer.ui.repository.list.RepositoryListContract
import com.diegomalone.githubviewer.ui.repository.list.RepositoryListPresenter
import com.nhaarman.mockito_kotlin.*
import io.reactivex.Observable
import junit.framework.Assert.assertFalse
import junit.framework.Assert.assertTrue
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.ArgumentMatchers.anyInt
import org.mockito.Mock
import org.mockito.Mockito.mock
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config

@RunWith(RobolectricTestRunner::class)
@Config(constants = BuildConfig::class, application = GithubViewerApplication::class, sdk = [26])
class RepositoryListPresenterTest : BaseTest() {

    @get:Rule
    val mockitoRule = daggerMockRule

    @Mock
    private lateinit var view: RepositoryListContract.View

    private lateinit var repositoryListPresenter: RepositoryListPresenter

    @Before
    fun setup() {
        mockitoRule.initMocks(this)

        repositoryListPresenter = RepositoryListPresenter(appComponent, githubDataSource)
        repositoryListPresenter.attachView(view)
    }

    @Test
    fun test_isListLoaded_loaded() {
        val testRepositoryList = GithubRepositoryFactory.repositoryList

        whenever(githubDataSource.fetchJavaRepositories(anyInt())).thenReturn(Observable.just(testRepositoryList))

        repositoryListPresenter.loadRepositoryList(true)

        assertTrue(repositoryListPresenter.isListLoaded())
    }

    @Test
    fun test_isListLoaded_empty() {
        val testRepositoryList = GithubRepositoryFactory.repositoryList

        whenever(githubDataSource.fetchJavaRepositories(anyInt())).thenReturn(Observable.just(testRepositoryList))

        assertFalse(repositoryListPresenter.isListLoaded())
    }

    @Test
    fun test_fetchRepository_success() {
        val testRepositoryList = GithubRepositoryFactory.repositoryList

        whenever(githubDataSource.fetchJavaRepositories(anyInt())).thenReturn(Observable.just(testRepositoryList))

        repositoryListPresenter.loadRepositoryList(true)

        verify(view).showLoadingIndicator(eq(false))
        verify(view).clearRepositoryList()
        verify(view).showRepositoryList(eq(testRepositoryList))
    }

    @Test
    fun test_fetchRepository_successSecondPage() {
        val testRepositoryList = GithubRepositoryFactory.repositoryList
        val secondRepositoryList = GithubRepositoryFactory.secondRepositoryList

        whenever(githubDataSource.fetchJavaRepositories(eq(1))).thenReturn(Observable.just(testRepositoryList))
        whenever(githubDataSource.fetchJavaRepositories(eq(2))).thenReturn(Observable.just(secondRepositoryList))

        repositoryListPresenter.loadRepositoryList(true)
        repositoryListPresenter.loadRepositoryList(false)

        verify(view).showRepositoryList(eq(testRepositoryList))
        verify(view).showRepositoryList(eq(secondRepositoryList))
    }

    @Test
    fun test_fetchRepository_noNetwork() {
        val noNetworkException = mock(NoNetworkException::class.java)

        whenever(githubDataSource.fetchJavaRepositories(anyInt())).thenReturn(Observable.error(noNetworkException))

        repositoryListPresenter.loadRepositoryList(true)

        verify(view).showLoadingIndicator(eq(false))
        verify(view, never()).clearRepositoryList()
        verify(view, never()).showRepositoryList(anyOrNull())

        verify(view).showNoNetworkError(anyOrNull())
        verify(view, never()).showUnexpectedError(anyOrNull())
    }

    @Test
    fun test_fetchRepository_serverError() {
        val exception = mock(Exception::class.java)

        whenever(githubDataSource.fetchJavaRepositories(anyInt())).thenReturn(Observable.error(exception))

        repositoryListPresenter.loadRepositoryList(true)

        verify(view).showLoadingIndicator(eq(false))
        verify(view, never()).clearRepositoryList()
        verify(view, never()).showRepositoryList(anyOrNull())

        verify(view).showUnexpectedError(anyOrNull())
        verify(view, never()).showNoNetworkError(anyOrNull())
    }
}