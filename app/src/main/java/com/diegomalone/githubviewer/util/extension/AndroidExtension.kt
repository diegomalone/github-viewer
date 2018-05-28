package com.diegomalone.githubviewer.util.extension

import android.content.Context
import com.diegomalone.githubviewer.base.GithubViewerApplication

val Context.appComponent
    get() = (applicationContext as GithubViewerApplication).appComponent