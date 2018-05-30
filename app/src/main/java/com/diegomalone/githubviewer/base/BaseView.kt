package com.diegomalone.githubviewer.base

import android.app.Activity
import java.lang.ref.WeakReference

interface BaseView {

    companion object {
        val DEFAULT_CLICK_LISTENER = {}
    }

    fun getViewActivity(): WeakReference<Activity>?
    fun finishActivity()

    fun showLoadingIndicator(loadingVisible: Boolean)
    fun showUnexpectedError(listener: () -> Unit = DEFAULT_CLICK_LISTENER)
    fun showNoNetworkError(listener: () -> Unit = DEFAULT_CLICK_LISTENER)
    fun hideErrorView()
}