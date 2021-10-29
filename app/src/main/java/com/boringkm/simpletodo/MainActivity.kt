package com.boringkm.simpletodo

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.boringkm.simpletodo.adapter.TodoItemAdapter
import com.boringkm.simpletodo.domain.TodoItem
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private var pressTime: Long = 0L
    private val testList = arrayListOf<TodoItem>(
        TodoItem(
            "달리기", false
        ),
        TodoItem(
            "매일 커밋", true
        ),
        TodoItem(
            "매일 QT", false
        )
    )


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val listView = findViewById<RecyclerView>(R.id.todoListView)
        listView.adapter = TodoItemAdapter(testList, this)
    }

    override fun onBackPressed() {
        if (System.currentTimeMillis() - pressTime < 2000) {
            finish()
        }
        Toast.makeText(this, "한 번 더 취소버튼을 누르시면 종료됩니다.", Toast.LENGTH_SHORT).show()
        pressTime = System.currentTimeMillis()
    }
}