package com.analog.taskly.domain.model
data class Todo(
    val id: Int = 0,
    val title: String,
    val description: String? = null,
    var isCompleted: Boolean = false,
    val priority: Priority = Priority.Default,
    val timestamp: Long = System.currentTimeMillis()
)
