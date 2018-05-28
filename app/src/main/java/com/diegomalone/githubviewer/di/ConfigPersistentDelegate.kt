package com.diegomalone.githubviewer.di

import android.content.Context
import android.os.Bundle
import com.diegomalone.githubviewer.di.module.PresenterModule
import com.diegomalone.githubviewer.util.extension.appComponent
import timber.log.Timber
import java.util.concurrent.atomic.AtomicLong

class ConfigPersistentDelegate {

    companion object {
        private const val KEY_ID = "KEY_ID"

        @JvmStatic
        private val NEXT_ID = AtomicLong(0)

        @JvmStatic
        private val componentsMap = HashMap<Long, ConfigPersistentComponent>()
    }

    private var id: Long = 0

    var instanceSaved = false
    lateinit var component: ConfigPersistentComponent
        get

    fun onCreate(context: Context, savedInstanceState: Bundle?) {
        // Create the ActivityComponent and reuses cached ConfigPersistentComponent if this is
        // being called after a configuration change.
        id = savedInstanceState?.getLong(KEY_ID) ?: NEXT_ID.getAndIncrement()

        if (componentsMap[id] != null)
            Timber.v("Reusing ConfigPersistentComponent id = %s", id)

        component = componentsMap.getOrPut(id, {
            Timber.v("Creating new ConfigPersistentComponent id = %s", id)
            context.appComponent + PresenterModule()
        })
    }

    fun onSaveInstanceState(outState: Bundle) {
        outState.putLong(KEY_ID, id)
        instanceSaved = true
    }

    fun onDestroy() {
        if (!instanceSaved) {
            Timber.v("Clearing ConfigPersistentComponent id = %s", id)
            componentsMap.remove(id)
        } else {
            Timber.v("Not Clearing ConfigPersistentComponent id = %s", id)
        }
    }
}