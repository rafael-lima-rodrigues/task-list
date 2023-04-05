package com.comunidadedevspace.taskbeats

import android.app.Activity
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.widget.Button
import android.widget.EditText
import com.comunidadedevspace.taskbeats.models.Task
import com.comunidadedevspace.taskbeats.utils.TASK_ACTION_RESULT
import com.google.android.material.snackbar.Snackbar

class TaskDetailActivity : AppCompatActivity() {

    private var task: Task? = null
    private lateinit var edtTitle: EditText
    private lateinit var edtDescription: EditText
    private lateinit var btnDone: Button

    companion object{
        private const val TASK_DETAIL_EXTRA = "task.extra.detail"

        fun start(context: Context, task: Task?): Intent {
            val intent = Intent(context, TaskDetailActivity::class.java)
                .apply {
                    putExtra(TaskDetailActivity.TASK_DETAIL_EXTRA, task)
                }
            return intent
        }
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_task_detail)

        task = intent.getSerializableExtra(TASK_DETAIL_EXTRA) as Task?

        edtTitle = findViewById(R.id.edt_task_title)
        edtDescription = findViewById(R.id.edt_task_description)
        btnDone = findViewById(R.id.btn_done)

        if (task != null) {
            edtTitle.setText(task!!.title)
            edtDescription.setText(task!!.description)
        }

        btnDone.setOnClickListener {
            val title = edtTitle.text.toString()
            val desc = edtDescription.text.toString()

            if (title.isNotEmpty() && desc.isNotEmpty()){
                if (task == null){
                    addOrUpdateNewTask(0,title, desc, ActionType.CREATE)
                } else {
                    addOrUpdateNewTask(task!!.id,title, desc, ActionType.UPDATE)
                }
            } else {
                showMessage(it, "Fields are required")
            }
        }

       // tvTitle = findViewById<TextView>(R.id.task_title_detail)

   //     tvTitle.text = task?.title ?: "Add one Task"
    }

    private fun addOrUpdateNewTask(id: Int, title: String, description: String, actionType: ActionType){
        val task = Task(id, title, description)
        returnAction(task, actionType)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val inflater: MenuInflater = menuInflater
        inflater.inflate(R.menu.menu_task_detail, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.delete_task -> {
                if (task != null) {
                    returnAction(task!!, ActionType.DELETE)
            } else{
                showMessage(btnDone, "Item not Found!")
                }
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun returnAction(task: Task, actionType: ActionType){
        val intent = Intent()
            .apply {
                val taskAction = TaskAction(task, actionType)
                putExtra(TASK_ACTION_RESULT, taskAction)
            }
        setResult(Activity.RESULT_OK, intent)
        finish()
    }

    private fun showMessage(view: View, message: String){
        Snackbar.make(view, message, Snackbar.LENGTH_LONG)
            .setAction("Action", null)
            .show()
    }
}