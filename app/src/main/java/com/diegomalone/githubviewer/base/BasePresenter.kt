package com.diegomalone.githubviewer.base

interface BasePresenter<in V : BaseView> {
    fun attachView(view: V)
    fun detachView()

    fun destroy()
}