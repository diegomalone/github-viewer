package com.diegomalone.githubviewer.network.github

import com.google.gson.annotations.SerializedName

data class GithubResponse<out T>(@SerializedName("items") val items: T)