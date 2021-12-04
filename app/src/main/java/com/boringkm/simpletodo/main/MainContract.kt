package com.boringkm.simpletodo.main

import com.boringkm.simpletodo.domain.Schedule


interface MainContract {
    interface View {
        fun getTodoList(list: List<Schedule>)
        fun getInsertResult(item: Schedule)
    }

    interface Presenter {
        fun start()
        fun insertItem(todoText: String)
    }
}