package com.diegomalone.githubviewer.repository.list

import android.content.Intent
import android.support.test.espresso.intent.rule.IntentsTestRule
import android.support.test.runner.AndroidJUnit4
import com.diegomalone.githubviewer.base.BaseInterfaceTest
import com.diegomalone.githubviewer.ui.repository.detail.RepositoryDetailsActivity
import com.diegomalone.githubviewer.ui.repository.list.RepositoryListActivity
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import java.net.HttpURLConnection

@RunWith(AndroidJUnit4::class)
class RepositoryListTest : BaseInterfaceTest() {

    @get:Rule
    private val intentsTestRule = IntentsTestRule(RepositoryListActivity::class.java, true, false)

    private val robot = RepositoryListRobot()

    @Before
    fun setup() {
        mockServer.startTestCase()
        intentsTestRule.launchActivity(Intent())
        mockServer.context = intentsTestRule.activity
    }

    @After
    fun tearDown() {
        intentsTestRule.finishActivity()
        mockServer.endTestCase()
    }

    @Test
    fun test_clickRepository() {
        mockServer.enqueue(HttpURLConnection.HTTP_OK, "repository_list_first_page.json")

        robot.repositoryClick(0)
                .redirectedTo(RepositoryDetailsActivity::class.java)
    }

    @Test
    fun test_repositoryIsShown() {
        mockServer.enqueue(HttpURLConnection.HTTP_OK, "repository_list_first_page.json")

        robot.checkRepositoryShown("java-design-patterns")
    }
}