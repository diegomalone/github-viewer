package com.diegomalone.githubviewer.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class GithubRepository(@SerializedName("name") val name: String,
                            @SerializedName("description") val description: String,
                            @SerializedName("stargazers_count") val totalStars: Int,
                            @SerializedName("forks_count") val totalForks: Int,
                            @SerializedName("owner") val owner: GithubUser) : Parcelable