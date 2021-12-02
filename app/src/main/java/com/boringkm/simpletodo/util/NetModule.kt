package com.boringkm.simpletodo.util

import android.content.Context
import com.boringkm.simpletodo.api.ScheduleService
import com.boringkm.simpletodo.api.UserService
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
class NetModule(private val context: Context) {

    private val url = "http://192.168.35.2:8080/"

    @Provides
    @Singleton
    fun provideContext(): Context = context

    @Provides
    @Singleton
    fun provideRetrofitBuilder(): Retrofit.Builder = Retrofit.Builder()

    @Provides
    @Singleton
    fun provideOkHttpBuilder(): OkHttpClient.Builder = OkHttpClient.Builder()

    @Provides
    @Singleton
    fun provideGson(): Gson = GsonBuilder().setLenient().create()

    @Provides
    @Singleton
    fun provideClient(builder: OkHttpClient.Builder, context: Context): OkHttpClient =
        builder.apply {
            connectTimeout(1, TimeUnit.MINUTES)
            readTimeout(30, TimeUnit.SECONDS)
            writeTimeout(30, TimeUnit.SECONDS)
        }.build()

    @Provides
    @Singleton
    @BaseUrl
    fun provideBaseRetrofit(builder: Retrofit.Builder, client: OkHttpClient, gson: Gson): Retrofit =
        builder.baseUrl(url)
            .client(client)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .build()

    @Provides
    @Singleton
    fun provideUserService(@BaseUrl retrofit: Retrofit): UserService = retrofit.create(UserService::class.java)

    @Provides
    @Singleton
    fun provideScheduleService(@BaseUrl retrofit: Retrofit): ScheduleService = retrofit.create(ScheduleService::class.java)
}