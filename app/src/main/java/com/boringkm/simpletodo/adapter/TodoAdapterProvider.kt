package com.boringkm.simpletodo.adapter

import com.boringkm.simpletodo.domain.ScheduleReq
import com.boringkm.simpletodo.domain.ScheduleRes
import com.boringkm.simpletodo.util.App
import com.boringkm.simpletodo.view.ProviderResultListener
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.Response

class TodoAdapterProvider(
    private val token: String
) {
    fun changeState(id: String, doneYn: Boolean, listener: ProviderResultListener) {
        val scope = CoroutineScope(Dispatchers.IO)
        scope.launch {
            val call = App.get().scheduleService.updateSchedule(token, ScheduleReq(
                doneYn = doneYn
            ), id)
            val response: Response<ScheduleRes> = call.execute()

            if (response.isSuccessful) {
                val result = response.body()
                if (result != null) {
                    withContext(Dispatchers.Main) {
                        listener.onResult(true, "$doneYn")
                    }
                }
            } else {
                withContext(Dispatchers.Main) {
                    listener.onResult(false, "change todo state error")
                }
            }
        }
    }
}