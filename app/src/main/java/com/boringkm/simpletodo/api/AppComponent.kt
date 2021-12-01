package com.boringkm.simpletodo.api

import com.boringkm.simpletodo.LoginActivity
import com.boringkm.simpletodo.MainActivity
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [NetModule::class])
interface AppComponent {
    fun inject(app: App)
    fun inject(mainActivity: MainActivity)
    fun inject(loginActivity: LoginActivity)
}