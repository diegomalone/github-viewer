package com.diegomalone.githubviewer.util

import com.diegomalone.githubviewer.util.extension.addToCompositeDisposable
import io.reactivex.Observable
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import java.util.concurrent.TimeUnit

object RxUtils {
    fun execute(schedulerContract: SchedulerContract, compositeDisposable: CompositeDisposable,
                success: () -> Unit = {}): Disposable {
        return delayExecution(0, schedulerContract, compositeDisposable, success)

    }

    fun delayExecution(timeout: Long, schedulerContract: SchedulerContract,
                       compositeDisposable: CompositeDisposable, success: () -> Unit = {}): Disposable {
        val request = Observable.just(true)
                .delay(timeout, TimeUnit.MILLISECONDS)
                .observeOn(schedulerContract.ui)
                .subscribeOn(schedulerContract.io)
                .subscribe({ success.invoke() })

        request.addToCompositeDisposable(compositeDisposable)

        return request
    }

    fun runOnUiThread(schedulerContract: SchedulerContract, success: () -> Unit = {}) {
        Observable.just(true)
                .observeOn(schedulerContract.ui)
                .subscribe({ success.invoke() })
    }
}