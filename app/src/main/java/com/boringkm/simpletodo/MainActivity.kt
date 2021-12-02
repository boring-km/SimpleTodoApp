package com.boringkm.simpletodo

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.inputmethod.EditorInfo
import android.widget.PopupMenu
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.boringkm.simpletodo.adapter.TodoItemAdapter
import com.boringkm.simpletodo.auth.Auth
import com.boringkm.simpletodo.domain.Schedule
import com.boringkm.simpletodo.domain.ScheduleReq
import com.boringkm.simpletodo.domain.ScheduleRes
import com.boringkm.simpletodo.util.App
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : BaseActivity() {

    private var auth: Auth? = null
    private var pressTime: Long = 0L
    private val testList = arrayListOf<Schedule>()
    private val todoItemAdapter = TodoItemAdapter(testList, this)


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        auth = Auth()
        val token = "Bearer ${intent.getStringExtra("idToken")}"

        val listView = findViewById<RecyclerView>(R.id.todoListView)
        listView.adapter = todoItemAdapter

        App.get().getAppComponent().inject(this)
        getTodoList(token)

        todoInputButton.setOnClickListener {
            val todoText = todoEditText.text.toString()
            if (todoText.isNotBlank()) {
                App.get().scheduleService.insertSchedule(token, ScheduleReq(title = todoText))
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe({ response: ScheduleRes -> run {
                        if (response.title == todoText) {
                            todoItemAdapter.add(
                                Schedule(
                                    title = todoText
                                )
                            )
                        }
                    }
                    }, { error -> Log.e("todo 항목 추가 에러", error.message!!)})
                    .apply {  }
                todoEditText.setText("")
            }
        }
        
        todoEditText.setOnEditorActionListener { _, actionId, _ ->
            if (actionId == EditorInfo.IME_ACTION_DONE || actionId == EditorInfo.IME_ACTION_GO) {
                todoInputButton.callOnClick()
            }
            return@setOnEditorActionListener false
        }

        user_menu.setOnClickListener { view ->
            val popupMenu = PopupMenu(applicationContext, view)
            menuInflater.inflate(R.menu.user_popup, popupMenu.menu)
            popupMenu.setOnMenuItemClickListener {
                if (it.itemId == R.id.user_menu1) {
                    logout()
                }
                return@setOnMenuItemClickListener false
            }
            popupMenu.show()
        }
    }

    private fun getTodoList(token: String) {
        App.get().scheduleService.getSchedule(token)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({ result: List<ScheduleRes> ->
                run {
                    for (item in result) {
                        testList.add(
                            item.convertScheduleReqToSchedule()
                        )
                    }
                }
            }, { error ->
                Log.d("error", error.message!!)
            }).apply { }
    }

    private fun logout() {
        auth!!.signOut()
        val intent = Intent(this@MainActivity, LoginActivity::class.java)
        startActivity(intent)
        finish()
    }

    override fun onBackPressed() {
        if (System.currentTimeMillis() - pressTime < 2000) {
            finishAffinity()
        }
        Toast.makeText(this, "한 번 더 취소버튼을 누르시면 종료됩니다.", Toast.LENGTH_SHORT).show()
        pressTime = System.currentTimeMillis()
    }
}