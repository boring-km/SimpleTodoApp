package com.boringkm.simpletodo.api

import com.boringkm.simpletodo.domain.ScheduleReq
import com.boringkm.simpletodo.domain.ScheduleRes
import io.reactivex.Single
import retrofit2.Call
import retrofit2.http.*

interface ScheduleService {

    @GET("/api/schedule")
    fun getSchedule(@Header("Authorization") token: String): Call<List<ScheduleRes>>

    @GET("/api/schedule/{title}")
    fun getScheduleWithTitle(@Header("Authorization") token: String, @Path("title") title: String): Call<List<ScheduleRes>>

    @POST("/api/schedule")
    fun insertSchedule(@Header("Authorization") token: String, @Body schedule: ScheduleReq): Call<ScheduleRes>

    @PUT("/api/schedule/{id}")
    fun updateSchedule(@Header("Authorization") token: String, @Body schedule: ScheduleReq, @Path("id") id: String): Call<ScheduleRes>

    @DELETE("/api/schedule/{id}")
    fun deleteSchedule(@Header("Authorization") token: String, @Body schedule: ScheduleReq, @Path("id") id: String): Call<ScheduleRes>
}