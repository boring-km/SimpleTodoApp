package com.boringkm.simpletodo.util

import org.junit.Test
import java.util.*

class TimeUtilsTest {

    @Test
    fun getCurrentTime() {
        println(TimeUtils().getCurrentTime(Locale.KOREA))
    }
}