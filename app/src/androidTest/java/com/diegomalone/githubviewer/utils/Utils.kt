package com.diegomalone.githubviewer.utils

import android.content.Context
import timber.log.Timber
import java.io.BufferedReader
import java.io.IOException
import java.io.InputStreamReader

object Utils {
    fun readFileFromAssets(context: Context?, fileName: String): String {
        if (context == null) return ""

        val builder = StringBuilder()
        try {
            val stream = context.assets.open(fileName)
            val bReader = BufferedReader(InputStreamReader(stream, "UTF-8"))
            var line: String? = bReader.readLine()

            while (line != null) {
                builder.append(line)
                line = bReader.readLine()
            }
        } catch (e: IOException) {
            Timber.e(e)
        }

        return builder.toString().substring(0)
    }
}