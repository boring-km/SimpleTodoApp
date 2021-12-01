package com.boringkm.simpletodo.api

import android.app.Application
import javax.inject.Inject

class App : Application() {

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