package com.boringkm.simpletodo.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import com.boringkm.simpletodo.R
import com.boringkm.simpletodo.domain.TodoItem
import kotlinx.android.synthetic.main.todo_item.view.*

class TodoItemAdapter (
    private val itemList: List<TodoItem>,
    private val activity: AppCompatActivity
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private lateinit var inflater: LayoutInflater
    private lateinit var context: Context

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        context = activity.applicationContext
        inflater = LayoutInflater.from(context)
        return TodoItemListHolder(parent)
    }

    inner class TodoItemListHolder(parent: ViewGroup) : RecyclerView.ViewHolder(
        inflater.inflate(
            R.layout.todo_item,
            parent,
            false
        )
    ) {
        val todoMenuButton: ImageButton = itemView.todoMenuButton
        val todoItemText: TextView = itemView.todoItemText
        val todoImage: ImageView = itemView.todoCheckedImage
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val selected = itemList[position]
        if (holder is TodoItemListHolder) {
            selected.run {
                holder.todoItemText.text = data
                if (checked) {
                    holder.todoImage.setImageResource(R.drawable.checked)
                } else {
                    holder.todoImage.setImageResource(R.drawable.unchecked)
                }
                holder.todoMenuButton.setOnClickListener {
                    Toast.makeText(context, "메뉴 버튼 클릭됨", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    override fun getItemCount(): Int {
        return itemList.size
    }
}