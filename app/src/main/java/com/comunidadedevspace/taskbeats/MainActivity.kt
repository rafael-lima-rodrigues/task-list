package com.comunidadedevspace.taskbeats

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.RecyclerView
import com.comunidadedevspace.taskbeats.adapter.TaskListAdapter
import com.comunidadedevspace.taskbeats.models.Task
import kotlinx.coroutines.newFixedThreadPoolContext

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val taskList: RecyclerView = findViewById(R.id.rv_task_list)

        val task0: Task = Task("Title0", "hello")
        val task1: Task = Task("Title1", "hello")
        val task2: Task = Task("Title2", "hello")

        val list = listOf<Task>(task0, task1, task2)
        val adapter = TaskListAdapter(list)

        taskList.adapter = adapter
    }
}