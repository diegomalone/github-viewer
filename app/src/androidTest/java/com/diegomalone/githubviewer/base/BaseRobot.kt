package com.diegomalone.githubviewer.base

import android.content.ComponentName
import android.content.Intent
import android.os.SystemClock
import android.support.test.InstrumentationRegistry
import android.support.test.espresso.Espresso
import android.support.test.espresso.assertion.ViewAssertions
import android.support.test.espresso.intent.Intents
import android.support.test.espresso.intent.matcher.IntentMatchers
import android.support.test.espresso.matcher.ViewMatchers
import com.diegomalone.githubviewer.R
import org.hamcrest.Matchers

open class BaseRobot {

    companion object {
        const val REDIRECT_CHECK_DELAY = 150L
        const val SNACK_CHECK_DELAY = 250L
        const val RECYCLER_VIEW_CHECK_DELAY = 300L
    }

    fun snackBarShown(text: String) = apply {
        SystemClock.sleep(SNACK_CHECK_DELAY)

        Espresso.onView(Matchers.allOf(ViewMatchers.withId(R.id.snackbar_text), ViewMatchers.withText(text)))
                .check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
    }

    fun snackBarShown(resId: Int) = apply {
        SystemClock.sleep(SNACK_CHECK_DELAY)

        Espresso.onView(Matchers.allOf(ViewMatchers.withId(R.id.snackbar_text), ViewMatchers.withText(resId)))
                .check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
    }

    fun failedDueNoNetwork() = apply {
        Espresso.onView(Matchers.allOf(ViewMatchers.withId(R.id.errorTitle),
                ViewMatchers.withText(R.string.error_no_network_connection)))
                .check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
    }

    fun failedDueUnknownError() = apply {
        Espresso.onView(Matchers.allOf(ViewMatchers.withId(R.id.errorTitle),
                ViewMatchers.withText(R.string.error_unexpected)))
                .check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
    }

    fun <T> redirectedTo(redirectClass: Class<T>) = apply {
        SystemClock.sleep(REDIRECT_CHECK_DELAY)
        Intents.intended(IntentMatchers.hasComponent(ComponentName(InstrumentationRegistry.getTargetContext(), redirectClass)))
    }

    fun redirectedToBrowser(url: String) = apply {
        SystemClock.sleep(REDIRECT_CHECK_DELAY)

        val expected = Matchers.allOf(IntentMatchers.hasAction(Intent.ACTION_VIEW), IntentMatchers.hasData(url))
        Intents.intended(expected)
    }
}