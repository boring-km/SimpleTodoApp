package com.boringkm.simpletodo.api

import com.boringkm.simpletodo.domain.ScheduleReq
import com.boringkm.simpletodo.domain.ScheduleRes
import retrofit2.http.*

interface ScheduleService {

    @GET("/schedule")
    fun getSchedule(): List<ScheduleRes>

    @GET("/schedule/{title}")
    fun getSchedule(@Path("title") title: String): List<ScheduleRes>

    @POST("/schedule")
    fun insertSchedule(@Body schedule: ScheduleReq): ScheduleRes

    @PUT("/schedule/{id}")
    fun updateSchedule(@Body schedule: ScheduleReq, @Path("id") id: String): ScheduleRes

    @DELETE("/schedule/{id}")
    fun deleteSchedule(@Body schedule: ScheduleReq, @Path("id") id: String): ScheduleRes
}