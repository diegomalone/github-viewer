package com.diegomalone.githubviewer.di.module

import android.app.Application
import android.content.Context
import com.diegomalone.githubviewer.base.GithubViewerApplication
import com.diegomalone.githubviewer.util.DefaultScheduler
import com.diegomalone.githubviewer.util.SchedulerContract
import dagger.Module
import dagger.Provides
import java.lang.ref.WeakReference
import javax.inject.Singleton

@Module
open class AppModule(private val application: GithubViewerApplication) {

    @Provides
    @Singleton
    open fun providesApplicationContext(): WeakReference<Context> = WeakReference(application)

    @Provides
    @Singleton
    open fun providesGithubViewerApplication(): WeakReference<GithubViewerApplication> = WeakReference(application)

    @Provides
    @Singleton
    open fun providesApplication(): WeakReference<Application> = WeakReference(application)

    @Provides
    open fun providesSchedulerContract(): SchedulerContract = DefaultScheduler

}