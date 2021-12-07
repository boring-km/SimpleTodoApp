package com.boringkm.simpletodo.view.main

import android.content.Intent
import android.os.Bundle
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import android.widget.PopupMenu
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.boringkm.simpletodo.R
import com.boringkm.simpletodo.adapter.TodoItemAdapter
import com.boringkm.simpletodo.auth.Auth
import com.boringkm.simpletodo.domain.Schedule
import com.boringkm.simpletodo.util.App
import com.boringkm.simpletodo.view.BaseActivity
import com.boringkm.simpletodo.view.login.LoginActivity
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : BaseActivity(), MainContract.View {

    private var auth: Auth = Auth()
    private var pressTime: Long = 0L
    private lateinit var todoItemAdapter: TodoItemAdapter
    private lateinit var presenter: MainContract.Presenter
    private lateinit var imm: InputMethodManager
    private lateinit var token: String


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        token = intent.getStringExtra("idToken")!!
        presenter = MainPresenter(this, token)
        App.get().getAppComponent().inject(this)
        imm = getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager

        initializeView()
    }

    private fun initializeView() {

        todoItemAdapter = TodoItemAdapter(this, token)
        val listView = findViewById<RecyclerView>(R.id.todoListView)
        listView.adapter = todoItemAdapter

        todoInputButton.setOnClickListener {
            val todoText = todoEditText.text.toString()
            presenter.insertItem(todoText)
            todoEditText.setText("")
        }

        todoEditText.setOnEditorActionListener { _, actionId, _ ->
            if (actionId == EditorInfo.IME_ACTION_DONE || actionId == EditorInfo.IME_ACTION_GO) {
                todoInputButton.callOnClick()
                imm.hideSoftInputFromWindow(todoEditText.windowToken, 0)
            }
            return@setOnEditorActionListener true
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

    override fun onResume() {
        super.onResume()
        presenter.start()
    }


    override fun getTodoList(list: List<Schedule>) {
        todoItemAdapter.addAll(list)
    }

    override fun getInsertResult(item: Schedule) {
        todoItemAdapter.add(item)
    }

    private fun logout() {
        auth.signOut()
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