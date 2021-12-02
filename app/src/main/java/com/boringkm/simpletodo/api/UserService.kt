package com.boringkm.simpletodo.api

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Header

interface UserService {

    @GET("/api/user")
    fun register(@Header("Authorization") token: String): Call<String>
}