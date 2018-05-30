package com.diegomalone.githubviewer.di.module

import android.app.Application
import android.content.Context
import android.net.ConnectivityManager
import com.diegomalone.githubviewer.BuildConfig
import com.diegomalone.githubviewer.exception.NoNetworkException
import com.diegomalone.githubviewer.network.github.GithubApi
import com.diegomalone.githubviewer.util.DateUtils.API_DATE_FORMAT
import com.diegomalone.githubviewer.util.extension.isConnected
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.lang.ref.WeakReference
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
open class ApiModule(private val apiUrl: String) {

    companion object Configuration {
        var apiUrl: String? = null

        const val CONNECTION_TIMEOUT = 15000L
        const val WRITE_TIMEOUT = 15000L
        const val READ_TIMEOUT = 30000L
    }

    @Provides
    @Singleton
    open fun providesOkHttpClient(app: WeakReference<Application>): OkHttpClient {
        val connectivityManager = app.get()?.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

        return getOkHttpBuilder()
                .connectTimeout(CONNECTION_TIMEOUT, TimeUnit.MILLISECONDS)
                .writeTimeout(WRITE_TIMEOUT, TimeUnit.MILLISECONDS)
                .readTimeout(READ_TIMEOUT, TimeUnit.MILLISECONDS)
                .addInterceptor { chain ->
                    val requestBuilder = chain.request().newBuilder()

                    if (!connectivityManager.isConnected) {
                        throw NoNetworkException()
                    }

                    chain.proceed(requestBuilder.build())
                }
                .build()
    }

    //region Rest APIs

    @Provides
    @Singleton
    open fun providesGithubApi(okHttpClient: OkHttpClient, gson: Gson): GithubApi {
        val retrofit = createRetrofit(okHttpClient, gson)

        return retrofit.create(GithubApi::class.java)
    }

    //endregion

    @Provides
    @Singleton
    open fun providesGson(): Gson =
            GsonBuilder()
                    .setDateFormat(API_DATE_FORMAT)
                    .serializeNulls()
                    .create()

    private fun getOkHttpBuilder(): OkHttpClient.Builder {

        return OkHttpClient.Builder()
                .addInterceptor(HttpLoggingInterceptor()
                        .apply {
                            level = if (BuildConfig.DEBUG) HttpLoggingInterceptor.Level.BODY
                            else HttpLoggingInterceptor.Level.NONE
                        }
                )
    }

    private fun createRetrofit(okHttpClient: OkHttpClient, gson: Gson): Retrofit {
        var usedApiUrl = apiUrl

        Configuration.apiUrl?.let {
            usedApiUrl = it
        }

        return Retrofit.Builder()
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create(gson))
                .baseUrl(usedApiUrl)
                .client(okHttpClient)
                .build()
    }

}