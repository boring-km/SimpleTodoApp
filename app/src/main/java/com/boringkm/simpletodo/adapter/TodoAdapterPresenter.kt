package com.boringkm.simpletodo.adapter

import android.util.Log
import com.boringkm.simpletodo.view.ProviderResultListener

class TodoAdapterPresenter(
    private val view: TodoAdapterContract.View,
    token: String
): TodoAdapterContract.Presenter {

    private val provider = TodoAdapterProvider(token)

    override fun changeState(id: String, doneYn: Boolean) {
        provider.changeState(id, doneYn, object : ProviderResultListener{
            override fun onResult(result: Boolean, message: String) {
                if (result) {
                    view.getChanged(id, doneYn)
                } else {
                    Log.e("API 호출 에러", message)
                }
            }
        })
    }

}