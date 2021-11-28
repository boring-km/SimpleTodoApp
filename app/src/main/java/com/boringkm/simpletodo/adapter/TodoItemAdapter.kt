package com.boringkm.simpletodo.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import com.boringkm.simpletodo.R
import com.boringkm.simpletodo.domain.TodoItem
import kotlinx.android.synthetic.main.todo_item.view.*

class TodoItemAdapter (
    private val itemList: MutableList<TodoItem>,
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
        val layout: LinearLayout = itemView.todoItemLayout
        val todoItemText: TextView = itemView.todoItemText
        val todoImage: ImageView = itemView.todoCheckedImage
        val todoMenuButton: ImageButton = itemView.todoMenuButton
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val selected = itemList[position]
        if (holder is TodoItemListHolder) {
            selected.run {
                holder.todoItemText.text = data
                holder.layout.setOnClickListener {
                    checked = !checked
                    showCheckedImage(holder)
                }
                showCheckedImage(holder)
                holder.todoMenuButton.setOnClickListener { view ->
                    val popupMenu = PopupMenu(context, view)
                    popupMenu.inflate(R.menu.item_popup)
                    popupMenu.setOnMenuItemClickListener {
                        when(it.itemId) {
                            R.id.item_menu1 -> Toast.makeText(context, "수정하기", Toast.LENGTH_SHORT).show()
                            R.id.item_menu2 -> Toast.makeText(context, "삭제하기", Toast.LENGTH_SHORT).show()
                        }
                        return@setOnMenuItemClickListener false
                    }
                    popupMenu.show()
                }
            }
        }
    }

    private fun TodoItem.showCheckedImage(holder: TodoItemListHolder) {
        if (checked) {
            holder.todoImage.setImageResource(R.drawable.checked)
        } else {
            holder.todoImage.setImageResource(R.drawable.unchecked)
        }
    }

    override fun getItemCount(): Int {
        return itemList.size
    }

    fun add(item: TodoItem) {
        itemList.add(item)
        notifyDataSetChanged()
    }

    fun set(todoList: List<TodoItem>) {
        itemList.clear()
        itemList.addAll(todoList)
        notifyDataSetChanged()
    }
}