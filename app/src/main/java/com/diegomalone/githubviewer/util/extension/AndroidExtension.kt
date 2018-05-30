package com.diegomalone.githubviewer.util.extension

import android.content.Context
import android.net.ConnectivityManager
import com.diegomalone.githubviewer.base.GithubViewerApplication

val Context.appComponent
    get() = (applicationContext as GithubViewerApplication).appComponent

val ConnectivityManager.isConnected: Boolean
    get() = activeNetworkInfo?.isConnected ?: false