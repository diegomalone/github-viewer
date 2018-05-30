package com.diegomalone.githubviewer.base

import android.os.Bundle

interface BasePresenter<in V : BaseView> {
    fun attachView(view: V)
    fun detachView()

    fun saveInstanceState(outState: Bundle?)
    fun restoreInstanceState(savedInstanceState: Bundle?)

    fun destroy()
}