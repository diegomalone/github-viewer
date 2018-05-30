package com.diegomalone.githubviewer.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class GithubPullRequest(@SerializedName("title") val title: String,
                             @SerializedName("html_url") val url: String) : Parcelable