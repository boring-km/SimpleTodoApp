package com.boringkm.simpletodo.domain

import java.util.*

data class Schedule(
    var id: String? = null,
    var userId: String? = null,
    var title: String? = null,
    var doneYn: Boolean? = false,
    var des: String? = null,
    var regDt: Date? = null
)

data class ScheduleReq(
    var userId: String? = null,
    var title: String? = null,
    var des: String? = null,
    var doneYn: Boolean? = false,
)
data class ScheduleRes(
    var id: String? = null,
    var userId: String? = null,
    var title: String? = null,
    var des: String? = null,
    var doneYn: Boolean? = false,
    var regDt: Date? = null
) {
    fun convertScheduleReqToSchedule(): Schedule {
        return Schedule(
            id, userId, title, doneYn, des, regDt
        )
    }
}
