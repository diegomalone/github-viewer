package com.diegomalone.githubviewer.repository.details

import android.content.Intent
import android.support.test.espresso.intent.rule.IntentsTestRule
import android.support.test.runner.AndroidJUnit4
import com.diegomalone.githubviewer.base.BaseInterfaceTest
import com.diegomalone.githubviewer.flow.FlowController.Companion.REPOSITORY_EXTRA
import com.diegomalone.githubviewer.model.GithubRepositoryFactory
import com.diegomalone.githubviewer.ui.repository.detail.RepositoryDetailsActivity
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import java.net.HttpURLConnection

@RunWith(AndroidJUnit4::class)
class RepositoryDetailsTest : BaseInterfaceTest() {

    @get:Rule
    private val intentsTestRule = IntentsTestRule(RepositoryDetailsActivity::class.java, true, false)

    private val robot = RepositoryDetailsRobot()

    @Before
    fun setup() {
        mockServer.startTestCase()
        val intent = Intent()
        intent.putExtra(REPOSITORY_EXTRA, GithubRepositoryFactory.testRepository)
        intentsTestRule.launchActivity(intent)

        mockServer.context = intentsTestRule.activity
    }

    @After
    fun tearDown() {
        intentsTestRule.finishActivity()
        mockServer.endTestCase()
    }

    @Test
    fun test_clickPullRequest() {
        mockServer.enqueue(HttpURLConnection.HTTP_OK, "pull_request_list.json")

        robot.pullRequestClick(0)
                .redirectedToBrowser("https://github.com/iluwatar/java-design-patterns/pull/753")
    }

    @Test
    fun test_pullRequestIsShown() {
        mockServer.enqueue(HttpURLConnection.HTTP_OK, "pull_request_list.json")

        robot.checkPullRequestShown("Acyclic Visitor pattern #734")
    }
}