package com.boringkm.simpletodo

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.inputmethod.EditorInfo
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.boringkm.simpletodo.adapter.TodoItemAdapter
import com.boringkm.simpletodo.auth.Auth
import com.boringkm.simpletodo.domain.TodoItem
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private var auth: Auth? = null
    private var pressTime: Long = 0L
    private val testList = arrayListOf<TodoItem>()
    private val todoItemAdapter = TodoItemAdapter(testList, this)


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        auth = Auth()

        val displayName = intent.getStringExtra("displayName")
        titleTextView.text = displayName

        val token = intent.getStringExtra("idToken")
        Toast.makeText(this, token, Toast.LENGTH_SHORT).show()

        val listView = findViewById<RecyclerView>(R.id.todoListView)
        listView.adapter = todoItemAdapter

        todoInputButton.setOnClickListener {
            val todoText = todoEditText.text.toString()
            if (todoText.isNotBlank()) {
                todoItemAdapter.add(
                    TodoItem(
                        todoText, false
                    )
                )
                todoEditText.setText("")
            }
        }
        
        todoEditText.setOnEditorActionListener { _, actionId, _ ->
            if (actionId == EditorInfo.IME_ACTION_DONE) {
                todoInputButton.callOnClick()
            }
            return@setOnEditorActionListener false
        }

        logoutButton.setOnClickListener {
            auth!!.signOut()
            val intent = Intent(this@MainActivity, LoginActivity::class.java)
            startActivity(intent)
            finish()
        }
    }

    override fun onBackPressed() {
        if (System.currentTimeMillis() - pressTime < 2000) {
            finishAffinity()
        }
        Toast.makeText(this, "한 번 더 취소버튼을 누르시면 종료됩니다.", Toast.LENGTH_SHORT).show()
        pressTime = System.currentTimeMillis()
    }
}