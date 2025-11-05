package com.analog.taskly.data.local.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.analog.taskly.domain.model.Priority

@Entity(tableName = "todo")
data class TodoEntity (
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val title: String,
    val description: String? = null,
    val isCompleted: Boolean = false,
    val priority: Priority = Priority.Default,
    val timestamp: Long = System.currentTimeMillis()
)