package com.diegomalone.githubviewer.repository.list

import android.os.SystemClock
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

class RepositoryListRobot : BaseRobot() {

    fun repositoryClick(position: Int) = apply {
        SystemClock.sleep(RECYCLER_VIEW_CHECK_DELAY)
        Espresso.onView(ViewMatchers.withId(R.id.repositoryListRecyclerView))
                .perform(RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(position, ViewActions.click()))
    }

    fun checkRepositoryShown(repositoryName: String) = apply {
        SystemClock.sleep(RECYCLER_VIEW_CHECK_DELAY)
        Espresso.onView(CoreMatchers.allOf<View>(ViewMatchers.withId(R.id.repositoryName), ViewMatchers.withText(repositoryName)))
                .check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
    }
}