package com.boringkm.simpletodo.util

import androidx.multidex.MultiDexApplication
import com.boringkm.simpletodo.api.ScheduleService
import com.boringkm.simpletodo.api.UserService
import javax.inject.Inject

class App : MultiDexApplication() {

    @Inject
    lateinit var userService: UserService

    @Inject
    lateinit var scheduleService: ScheduleService

    private lateinit var appComponent: AppComponent

    override fun onCreate() {
        super.onCreate()
        app = this
        configureAppComponent()
    }

    fun getAppComponent(): AppComponent {
        return appComponent
    }

    private fun configureAppComponent() {
        appComponent = buildAppComponent()
        appComponent.inject(this)
    }

    private fun buildAppComponent(): AppComponent {
        return DaggerAppComponent.builder()
            .netModule(NetModule(this.applicationContext))
            .build()
    }

    companion object {
        private lateinit var app: App

        fun get(): App {
            return app
        }
    }
}