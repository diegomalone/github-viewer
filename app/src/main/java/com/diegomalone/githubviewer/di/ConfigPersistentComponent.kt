package com.diegomalone.githubviewer.di

import com.diegomalone.githubviewer.di.module.ActivityModule
import com.diegomalone.githubviewer.di.module.PresenterModule
import dagger.Subcomponent

@ConfigPersistent
@Subcomponent(modules = [PresenterModule::class])
interface ConfigPersistentComponent {
    operator fun plus(activityModule: ActivityModule): ActivityComponent
}