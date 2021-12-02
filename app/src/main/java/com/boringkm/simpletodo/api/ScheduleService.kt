package com.boringkm.simpletodo.api

import com.boringkm.simpletodo.domain.ScheduleReq
import com.boringkm.simpletodo.domain.ScheduleRes
import io.reactivex.Single
import retrofit2.http.*

interface ScheduleService {

    @GET("/schedule")
    fun getSchedule(@Header("Authorization") token: String): Single<List<ScheduleRes>>

    @GET("/schedule/{title}")
    fun getScheduleWithTitle(@Header("Authorization") token: String, @Path("title") title: String): Single<List<ScheduleRes>>

    @POST("/schedule")
    fun insertSchedule(@Header("Authorization") token: String, @Body schedule: ScheduleReq): Single<ScheduleRes>

    @PUT("/schedule/{id}")
    fun updateSchedule(@Header("Authorization") token: String, @Body schedule: ScheduleReq, @Path("id") id: String): Single<ScheduleRes>

    @DELETE("/schedule/{id}")
    fun deleteSchedule(@Header("Authorization") token: String, @Body schedule: ScheduleReq, @Path("id") id: String): Single<ScheduleRes>
}