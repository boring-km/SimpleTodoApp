package com.boringkm.simpletodo.view.login

import com.boringkm.simpletodo.util.App
import com.boringkm.simpletodo.view.ProviderResultListener
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.Response

class LoginProvider(
    private val token: String
) {
    var isChecked = false

    @Suppress("BlockingMethodInNonBlockingContext")
    fun callData(listener: ProviderResultListener) {
        val scope = CoroutineScope(Dispatchers.IO)
        scope.launch {
            val call = App.get().userService.register("Bearer $token")
            val response: Response<String> = call.execute()
            if (response.isSuccessful) {
                val result = response.body()
                if (result != null && result == "SUCCESS") {
                    isChecked = true
                    withContext(Dispatchers.Main) {
                        listener.onResult(true, "")
                    }
                }
            } else {
                withContext(Dispatchers.Main) {
                    listener.onResult(false, "로그인 실패")
                }
            }
        }
    }
}