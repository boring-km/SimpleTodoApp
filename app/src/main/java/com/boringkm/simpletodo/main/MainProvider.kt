package com.boringkm.simpletodo.main

import com.boringkm.simpletodo.domain.Schedule
import com.boringkm.simpletodo.domain.ScheduleReq
import com.boringkm.simpletodo.domain.ScheduleRes
import com.boringkm.simpletodo.util.App
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.Call
import retrofit2.Response

interface ProviderResultListener {
    fun onResult(result: Boolean, message: String)
}

class MainProvider(
    private val token: String
) {
    var todoList = arrayListOf<Schedule>()
    var insertedItem = Schedule()

    fun callData(listener: ProviderResultListener) {

        val scope = CoroutineScope(Dispatchers.IO)
        scope.launch {
            val call: Call<List<ScheduleRes>> = App.get().scheduleService.getSchedule(token)
            val response: Response<List<ScheduleRes>> = call.execute()
            if (response.isSuccessful) {
                val result = response.body()
                if (result != null) {
                    for (item in result) {
                        todoList.add(
                            item.convertScheduleReqToSchedule()
                        )
                    }
                    withContext(Dispatchers.Main) {
                        listener.onResult(true, "")
                    }
                }
            } else {
                withContext(Dispatchers.Main) {
                    listener.onResult(false, "get todo itemList error")
                }
            }
        }
    }

    fun insertItem(todoText: String, listener: ProviderResultListener) {
        if (todoText.isNotBlank()) {
            val scope = CoroutineScope(Dispatchers.IO)
            scope.launch {
                val call = App.get().scheduleService.insertSchedule(token, ScheduleReq(title = todoText))
                val response: Response<ScheduleRes> = call.execute()
                if (response.isSuccessful) {
                    val result = response.body()
                    if (result != null) {
                        insertedItem = result.convertScheduleReqToSchedule()
                        withContext(Dispatchers.Main) {
                            listener.onResult(true, "")
                        }
                    } else {
                        withContext(Dispatchers.Main) {
                            listener.onResult(false, "todo item insert error")
                        }
                    }
                }
            }
        }
    }
}