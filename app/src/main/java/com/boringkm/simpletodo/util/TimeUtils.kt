package com.boringkm.simpletodo.util

import android.content.Context
import android.os.Build
import java.text.SimpleDateFormat
import java.util.*

class TimeUtils {
    fun getCurrentTime(locale: Locale): String {
        val now = System.currentTimeMillis()
        val date = Date(now)
        val dateFormat = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", locale)
        return dateFormat.format(date)
    }

    fun getCurrentLocale(context: Context): Locale? {
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            context.resources.configuration.locales.get(0)
        } else {
            @Suppress("DEPRECATION")
            context.resources.configuration.locale
        }
    }
}