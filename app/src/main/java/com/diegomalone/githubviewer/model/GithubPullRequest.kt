package com.diegomalone.githubviewer.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class GithubPullRequest(@SerializedName("title") val title: String,
                             @SerializedName("body") val body: String,
                             @SerializedName("html_url") val url: String,
                             @SerializedName("created_at") val createdAt: String,
                             @SerializedName("user") val user: GithubUser) : Parcelable