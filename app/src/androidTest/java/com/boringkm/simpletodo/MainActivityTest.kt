package com.boringkm.simpletodo

import android.widget.Button
import android.widget.EditText
import androidx.recyclerview.widget.RecyclerView
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.contrib.RecyclerViewActions
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.boringkm.simpletodo.adapter.TodoItemAdapter
import com.boringkm.simpletodo.domain.TodoItem
import org.hamcrest.Matchers.allOf
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith


@RunWith(AndroidJUnit4::class)
class MainActivityTest {

    @get:Rule
    var activityScenarioRule: ActivityScenarioRule<MainActivity> =
        ActivityScenarioRule(MainActivity::class.java)

    private var todoList = arrayListOf(
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

    @Before
    fun setUp() {
        activityScenarioRule.scenario.onActivity { activity ->
            val listView = activity.findViewById<RecyclerView>(R.id.todoListView)
            listView?.let { it ->
                val addList = arrayListOf(
                    TodoItem(
                        "달리기", false
                    ),
                    TodoItem(
                        "매일 커밋", true
                    )
                )
                (it.adapter as TodoItemAdapter).set(addList)
                val editText = activity.findViewById<EditText>(R.id.todoEditText)
                editText.setText("매일 QT")
                activity.findViewById<Button>(R.id.todoInputButton).callOnClick()
            }
        }
    }

    @Before
    fun 새로운_항목을_입력하고_입력_버튼을_누르기() {
        activityScenarioRule.scenario.onActivity { activity ->
            activity.findViewById<EditText>(R.id.todoEditText).setText("매일 QT")
            activity.findViewById<Button>(R.id.todoInputButton).callOnClick()
        }
    }

    @Test
    fun 화면에_추가한_3가지_항목이_모두_나왔는지_확인() {
        var position = 0
        todoList.forEach {
            onView(withId(R.id.todoListView))
                .perform(
                    RecyclerViewActions.scrollToPosition<TodoItemAdapter.TodoItemListHolder>(
                        position
                    )
                )
                .check(matches(hasDescendant(allOf(withText(it.data), isDisplayed()))))
            position++
        }
    }
}