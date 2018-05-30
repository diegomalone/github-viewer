package com.diegomalone.githubviewer.base

import com.diegomalone.githubviewer.util.SchedulerContract
import io.reactivex.disposables.CompositeDisposable
import javax.inject.Inject

abstract class GithubViewerBasePresenter<V : BaseView> : BasePresenter<V> {

    protected val compositeDisposable = CompositeDisposable()

    @Inject
    protected lateinit var scheduler: SchedulerContract

    var view: V? = null

    override fun attachView(view: V) {
        this.view = view
    }

    override fun detachView() {
        this.view = null
    }

    override fun destroy() {
        compositeDisposable.clear()
    }
}