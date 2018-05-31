package com.diegomalone.githubviewer.base

import com.diegomalone.githubviewer.BuildConfig
import com.diegomalone.githubviewer.di.AppComponent
import com.diegomalone.githubviewer.di.module.ApiModule
import com.diegomalone.githubviewer.di.module.DataModule
import com.diegomalone.githubviewer.network.GithubDataSource
import com.diegomalone.githubviewer.utils.TestAppModule
import it.cosenonjaviste.daggermock.DaggerMock
import it.cosenonjaviste.daggermock.DaggerMockRule
import it.cosenonjaviste.daggermock.InjectFromComponent
import org.robolectric.RuntimeEnvironment

open class BaseTest {

    protected val daggerMockRule: DaggerMockRule<AppComponent> =
            DaggerMock.rule<AppComponent>(TestAppModule((RuntimeEnvironment.application as GithubViewerApplication)),
                    ApiModule(BuildConfig.API_URL),
                    DataModule()) {
                providesMock<GithubDataSource>()
            }.set { component -> component?.let { appComponent = it } }

    protected lateinit var appComponent: AppComponent

    @InjectFromComponent
    protected lateinit var githubDataSource: GithubDataSource
}