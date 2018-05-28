package com.diegomalone.githubviewer.base

import android.app.Activity
import java.lang.ref.WeakReference

interface BaseView {
    fun getViewActivity(): WeakReference<Activity>?
    fun finishActivity()

    fun showLoadingIndicator(loadingVisible: Boolean)
    fun showUnexpectedError()
}