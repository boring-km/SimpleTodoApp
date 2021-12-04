package com.boringkm.simpletodo.main

import android.util.Log

class MainPresenter(
    private val view: MainContract.View,
    private val token: String
): MainContract.Presenter {

    var provider: MainProvider = MainProvider(token)

    override fun start() {
        provider.callData(object : ProviderResultListener {
            override fun onResult(result: Boolean, message: String) {
                if (result) {
                    view.getTodoList(provider.todoList)
                } else {
                    Log.e("API 호출 에러", message)
                }
            }
        })
    }

    override fun insertItem(todoText: String) {
        provider.insertItem(todoText, object : ProviderResultListener {
            override fun onResult(result: Boolean, message: String) {
                if (result) {
                    view.getInsertResult(provider.insertedItem)
                } else {
                    Log.e("API 호출 에러", message)
                }
            }
        })
    }
}