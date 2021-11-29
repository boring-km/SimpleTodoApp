package com.boringkm.simpletodo.util

import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit


class ApiHelper private constructor() {
    private var mMapRetrofit: HashMap<Any, Any?>? = null
    private var mRetrofit: Retrofit? = null
    val client: Retrofit?
        get() {
            if (mMapRetrofit == null) mMapRetrofit = HashMap()
            if (!mMapRetrofit!!.containsKey("baseurl")) {
                mRetrofit = buildClient()
                mMapRetrofit!!["baseurl"] = mRetrofit
            }
            return mRetrofit
        }

    private fun buildClient(): Retrofit? {
        val client: OkHttpClient = OkHttpClient.Builder()
            .connectTimeout(1, TimeUnit.MINUTES)
            .readTimeout(30, TimeUnit.SECONDS)
            .writeTimeout(30, TimeUnit.SECONDS)
            .build()
        mRetrofit = Retrofit.Builder().baseUrl(BaseUrl)
            .client(client)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        return mRetrofit
    }

    private object LazyHolder {
        val instance = ApiHelper()
    }

    companion object {
        private const val BaseUrl = "http://localhost"
    }
}