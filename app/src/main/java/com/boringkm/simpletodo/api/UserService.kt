package com.boringkm.simpletodo.api

import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Header

interface UserService {

    @GET("/user")
    fun register(@Header("Authorization") token: String): Single<String>
}