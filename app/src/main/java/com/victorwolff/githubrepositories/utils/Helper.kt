package com.victorwolff.githubrepositories.utils

import android.content.Context
import android.content.res.Resources
import com.victorwolff.githubrepositories.R
import java.io.InputStream
import java.lang.Exception
import java.util.*

object Helper {
    private const val TAG = "Helper"
    fun getConfigValue(context: Context, name: String?): String? {
        val resources: Resources = context.resources
        return try {
            val rawResource: InputStream = resources.openRawResource(R.raw.config)
            val properties = Properties()
            properties.load(rawResource)
            properties.getProperty(name)
        } catch (e: Exception) {
            null
        }
    }
}