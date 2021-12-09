package com.boringkm.simpletodo.adapter

interface TodoAdapterContract {
    interface View {
        fun getChanged(id: String, doneYn: Boolean)
    }

    interface Presenter {
        fun changeState(id: String, doneYn: Boolean)
    }
}