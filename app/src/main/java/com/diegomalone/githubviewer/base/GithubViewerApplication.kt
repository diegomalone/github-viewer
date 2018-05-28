package com.diegomalone.githubviewer.base

import android.app.Application
import com.diegomalone.githubviewer.BuildConfig
import com.diegomalone.githubviewer.di.AppComponent
import com.diegomalone.githubviewer.di.DaggerAppComponent
import com.diegomalone.githubviewer.di.module.ApiModule
import com.diegomalone.githubviewer.di.module.AppModule
import io.reactivex.plugins.RxJavaPlugins
import timber.log.Timber

class GithubViewerApplication : Application() {

    lateinit var appComponent: AppComponent

    override fun onCreate() {
        super.onCreate()

        buildComponent()
        configureExceptionLogging()
    }

    private fun configureExceptionLogging() {
        val default = Thread.getDefaultUncaughtExceptionHandler()

        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        }

        Thread.setDefaultUncaughtExceptionHandler { thread, e ->
            Timber.e(e)
            default.uncaughtException(thread, e)
        }

        RxJavaPlugins.setErrorHandler(Timber::e)
    }

    private fun buildComponent() {
        appComponent = DaggerAppComponent.builder()
                .appModule(AppModule(this))
                .apiModule(ApiModule(BuildConfig.API_URL))
                .build()
    }
}