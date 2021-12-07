package com.boringkm.simpletodo.util

import com.boringkm.simpletodo.adapter.TodoItemAdapter
import com.boringkm.simpletodo.view.login.LoginActivity
import com.boringkm.simpletodo.view.main.MainActivity
import com.boringkm.simpletodo.view.login.SplashActivity
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [NetModule::class])
interface AppComponent {
    fun inject(app: App)
    fun inject(mainActivity: MainActivity)
    fun inject(loginActivity: LoginActivity)
    fun inject(splashActivity: SplashActivity)
    fun inject(todoItemAdapter: TodoItemAdapter)
}