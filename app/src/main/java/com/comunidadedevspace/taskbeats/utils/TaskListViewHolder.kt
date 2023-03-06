package com.comunidadedevspace.taskbeats.utils

import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.comunidadedevspace.taskbeats.R
import com.comunidadedevspace.taskbeats.models.Task

class TaskListViewHolder(view: View) : RecyclerView.ViewHolder(view){

    private val tvTaskTitle: TextView = view.findViewById<TextView>(R.id.tv_task_title)
    private val tvTaskDescription: TextView = view.findViewById<TextView>(R.id.tv_task_description)

    fun bind(task: Task){
       tvTaskTitle.text = task.title
       tvTaskDescription.text = task.description
    }
}