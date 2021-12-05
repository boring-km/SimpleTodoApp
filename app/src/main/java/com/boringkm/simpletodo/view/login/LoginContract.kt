package com.boringkm.simpletodo.view.login

interface LoginContract {
    interface View {
        fun login(token: String)
    }

    interface Presenter {
        fun start()
    }
}