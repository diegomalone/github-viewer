package com.diegomalone.githubviewer.utils

import com.diegomalone.githubviewer.base.GithubViewerApplication
import com.diegomalone.githubviewer.di.module.AppModule
import com.diegomalone.githubviewer.util.ImmediateScheduler
import com.diegomalone.githubviewer.util.SchedulerContract

open class TestAppModule(personasApplication: GithubViewerApplication) : AppModule(personasApplication) {

    override fun providesSchedulerContract(): SchedulerContract {
        return ImmediateScheduler
    }
}