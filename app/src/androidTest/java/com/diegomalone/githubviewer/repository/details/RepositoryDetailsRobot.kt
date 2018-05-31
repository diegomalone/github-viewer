package com.diegomalone.githubviewer.repository.details

import android.os.SystemClock
import android.support.test.InstrumentationRegistry
import android.support.test.espresso.Espresso
import android.support.test.espresso.action.ViewActions
import android.support.test.espresso.assertion.ViewAssertions
import android.support.test.espresso.contrib.RecyclerViewActions
import android.support.test.espresso.matcher.ViewMatchers
import android.support.v7.widget.RecyclerView
import android.view.View
import com.diegomalone.githubviewer.R
import com.diegomalone.githubviewer.base.BaseRobot
import org.hamcrest.CoreMatchers

class RepositoryDetailsRobot : BaseRobot() {

    fun pullRequestClick(position: Int) = apply {
        SystemClock.sleep(RECYCLER_VIEW_CHECK_DELAY)
        Espresso.onView(ViewMatchers.withId(R.id.pullRequestListRecyclerView))
                .perform(RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(position, ViewActions.click()))
    }

    fun checkPullRequestShown(repositoryName: String) = apply {
        SystemClock.sleep(RECYCLER_VIEW_CHECK_DELAY)
        Espresso.onView(CoreMatchers.allOf<View>(ViewMatchers.withId(R.id.pullRequestTitle), ViewMatchers.withText(repositoryName)))
                .check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
    }

    fun checkPullRequestCount(count: Int) = apply {
        SystemClock.sleep(RECYCLER_VIEW_CHECK_DELAY)

        val context = InstrumentationRegistry.getTargetContext()
        val pullRequestCount = context.resources.getQuantityString(R.plurals.repository_details_pull_request_count_pattern, count, count)

        Espresso.onView(CoreMatchers.allOf<View>(ViewMatchers.withId(R.id.listTitle), ViewMatchers.withText(pullRequestCount)))
                .check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
    }

}