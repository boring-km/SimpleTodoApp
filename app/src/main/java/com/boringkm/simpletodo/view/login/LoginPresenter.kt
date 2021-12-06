package com.boringkm.simpletodo.view.login

import android.util.Log
import com.boringkm.simpletodo.view.ProviderResultListener

class LoginPresenter (
    private val view: LoginContract.View,
    private val token: String
) : LoginContract.Presenter {

    private var provider = LoginProvider(token)

    override fun start() {
        provider.callData(object : ProviderResultListener {
            override fun onResult(result: Boolean, message: String) {
                if (result) {
                    if (provider.isChecked) {
                        view.login("Bearer $token")
                    }
                } else {
                    Log.e("API 호출 에러", message)
                }
            }
        })
    }

}