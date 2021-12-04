package com.boringkm.simpletodo

import android.content.Intent
import android.os.Bundle
import android.view.inputmethod.EditorInfo
import android.widget.PopupMenu
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.boringkm.simpletodo.adapter.TodoItemAdapter
import com.boringkm.simpletodo.auth.Auth
import com.boringkm.simpletodo.domain.Schedule
import com.boringkm.simpletodo.main.MainContract
import com.boringkm.simpletodo.main.MainPresenter
import com.boringkm.simpletodo.util.App
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : BaseActivity(), MainContract.View {

    private var auth: Auth = Auth()
    private var pressTime: Long = 0L
    private val itemList = arrayListOf<Schedule>()
    private val todoItemAdapter = TodoItemAdapter(itemList, this)
    private lateinit var presenter: MainContract.Presenter


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val token = "Bearer ${intent.getStringExtra("idToken")}"
        presenter = MainPresenter(this, token)
        App.get().getAppComponent().inject(this)

        initializeView()
    }

    private fun initializeView() {

        val listView = findViewById<RecyclerView>(R.id.todoListView)
        listView.adapter = todoItemAdapter

        todoInputButton.setOnClickListener {
            val todoText = todoEditText.text.toString()
            presenter.insertItem(todoText)
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

    override fun onResume() {
        super.onResume()
        presenter.start()
    }


    override fun getTodoList(list: List<Schedule>) {
        itemList.clear()
        itemList.addAll(list)
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