package com.boringkm.simpletodo.utils

import android.app.Application
import android.util.Log
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.boringkm.simpletodo.util.TimeUtils
import org.junit.Test
import org.junit.runner.RunWith
import java.util.*

@RunWith(AndroidJUnit4::class)
class TimeUtilsTest {
    @Test
    fun getLocaleTest() {
        val context = ApplicationProvider.getApplicationContext<Application>()
        val it: Locale = TimeUtils().getCurrentLocale(context)!!
        Log.d("locale", it.country)
        val time = TimeUtils().getCurrentTime(it)
        Log.d("current time", time)
    }
}