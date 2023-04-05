package com.comunidadedevspace.taskbeats

import com.comunidadedevspace.taskbeats.models.Task
import java.io.Serializable

data class TaskAction(val task: Task, val actionType: ActionType) : Serializable