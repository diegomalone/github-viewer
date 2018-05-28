package com.diegomalone.githubviewer.util

import io.reactivex.Scheduler
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.internal.schedulers.ImmediateThinScheduler
import io.reactivex.schedulers.Schedulers

interface SchedulerContract {
    val io: Scheduler
    val ui: Scheduler
}

object DefaultScheduler : SchedulerContract {
    override val io: Scheduler = Schedulers.io()
    override val ui: Scheduler = AndroidSchedulers.mainThread()
}

object ImmediateScheduler : SchedulerContract {
    private val scheduler: Scheduler = ImmediateThinScheduler.INSTANCE

    override val io: Scheduler = scheduler
    override val ui: Scheduler = scheduler
}