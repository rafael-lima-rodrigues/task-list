package com.comunidadedevspace.taskbeats

import android.app.Activity
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.LinearLayout
import androidx.activity.result.ActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.recyclerview.widget.RecyclerView
import com.comunidadedevspace.taskbeats.adapter.TaskListAdapter
import com.comunidadedevspace.taskbeats.models.Task
import com.comunidadedevspace.taskbeats.utils.TASK_ACTION_RESULT
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.snackbar.Snackbar
import java.io.Serializable

class MainActivity : AppCompatActivity() {
    private var taskList = arrayListOf(
        Task(0,"Academia", "Treino de Boxe"),
        Task(1,"Mercado", "Comprar ovo e leite"),
        Task(2,"Estudos", "Fazer novas aulas do DevSpace")
    )

    private lateinit var ctnContent: LinearLayout

    private val adapter = TaskListAdapter(::onListItemClicked)

    private val startForResult = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) {
            result: ActivityResult ->
        if (result.resultCode == Activity.RESULT_OK) {
            val data = result.data
            val taskAction = data?.getSerializableExtra(TASK_ACTION_RESULT) as TaskAction
            val task: Task = taskAction.task

            if (taskAction.actionType == ActionType.DELETE){
                val newList = arrayListOf<Task>()
                    .apply {
                        addAll(taskList)
                    }
                 newList.remove(task)

                showMessage(ctnContent, "Item deleted ${task.title}")

                if (newList.isEmpty()){
                    ctnContent.visibility = View.VISIBLE
                }
                adapter.submitList(newList)
                taskList = newList

            } else if (taskAction.actionType == ActionType.CREATE){
                val newList = arrayListOf<Task>()
                    .apply {
                        addAll(taskList)
                    }
                newList.add(task)

                showMessage(ctnContent, "Item added ${task.title}")
                adapter.submitList(newList)
                taskList = newList
            } else if (taskAction.actionType == ActionType.UPDATE){
                val tempList = arrayListOf<Task>()
                taskList.forEach {
                    if (it.id == task.id){
                        val newItem = Task(it.id, task.title, task.description)
                        tempList.add(newItem)
                    } else {
                        tempList.add(it)
                    }
                }
                showMessage(ctnContent, "Item updated ${task.title}")
                adapter.submitList(tempList)
                taskList = tempList
            }

        }
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_task_list)

        ctnContent = findViewById(R.id.ctn_content)
        adapter.submitList(taskList)

        val rvTasks: RecyclerView = findViewById(R.id.rv_task_list)
        rvTasks.adapter = adapter

        val fab =findViewById<FloatingActionButton>(R.id.fab_add)
        fab.setOnClickListener {
            openTaskListDetail()
        }
    }

    private fun onListItemClicked(task:Task){
        openTaskListDetail(task)
    }

    private fun openTaskListDetail(task: Task? = null) {
        val intent = TaskDetailActivity.start(this, task)
        startForResult.launch(intent)
    }

    private fun showMessage(view: View, message: String){
        Snackbar.make(view, message, Snackbar.LENGTH_LONG)
            .setAction("Action", null)
            .show()
    }
}

enum class ActionType {

     DELETE,
     UPDATE,
     CREATE,
}
