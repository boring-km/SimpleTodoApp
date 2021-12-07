package com.boringkm.simpletodo.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import com.boringkm.simpletodo.R
import com.boringkm.simpletodo.domain.Schedule
import com.boringkm.simpletodo.domain.ScheduleReq
import com.boringkm.simpletodo.util.App
import com.google.android.gms.common.api.Api
import kotlinx.android.synthetic.main.todo_item.view.*

class TodoItemAdapter (
    private val activity: AppCompatActivity,
    private val token: String
) : RecyclerView.Adapter<RecyclerView.ViewHolder>(), TodoAdapterContract.View {

    private val itemList: MutableList<Schedule> = arrayListOf()

    private lateinit var inflater: LayoutInflater
    private lateinit var context: Context
    private lateinit var presenter: TodoAdapterPresenter

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        context = activity.applicationContext
        presenter = TodoAdapterPresenter(this, token)
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
                showCheckedImage(holder)
                holder.todoItemText.text = title
                holder.layout.setOnClickListener {
                    presenter.changeState(id!!, !doneYn!!)
                }
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

    private fun Schedule.showCheckedImage(holder: TodoItemListHolder) {
        if (doneYn!!) {
            holder.todoImage.setImageResource(R.drawable.checked)
        } else {
            holder.todoImage.setImageResource(R.drawable.unchecked)
        }
    }

    override fun getItemCount(): Int {
        return itemList.size
    }

    fun add(item: Schedule) {
        itemList.add(item)
        notifyDataSetChanged()
    }

    fun addAll(todoList: List<Schedule>) {
        itemList.clear()
        itemList.addAll(todoList)
        notifyDataSetChanged()
    }

    override fun getChanged(id: String, doneYn: Boolean) {
        for (i in 0..itemList.size) {
            if (itemList[i].id == id) {
                itemList[i].doneYn = doneYn
                notifyDataSetChanged()
                break
            }
        }
    }
}