package com.comunidadedevspace.taskbeats.models

import java.io.Serializable

data class Task(
    val id: Int,
    var title: String,
    var description: String
    ): Serializable{

}