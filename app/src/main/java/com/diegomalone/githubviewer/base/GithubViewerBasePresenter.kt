package com.diegomalone.githubviewer.base

import com.diegomalone.githubviewer.flow.FlowController
import com.diegomalone.githubviewer.flow.FlowControllerImpl
import com.diegomalone.githubviewer.util.SchedulerContract
import io.reactivex.disposables.CompositeDisposable
import javax.inject.Inject

abstract class GithubViewerBasePresenter<V : BaseView> : BasePresenter<V> {

    protected val compositeDisposable = CompositeDisposable()

    @Inject
    protected lateinit var scheduler: SchedulerContract

    protected var flowController: FlowController? = null

    var view: V? = null

    override fun attachView(view: V) {
        this.view = view
        flowController = FlowControllerImpl(view.getViewActivity())
    }

    override fun detachView() {
        this.view = null
        this.flowController = null
    }

    override fun destroy() {
        compositeDisposable.clear()
    }
}