package com.analog.taskly.data.mapper

import com.analog.taskly.data.local.entities.TodoEntity
import com.analog.taskly.domain.model.Todo

fun TodoEntity.toDomain() = Todo(
    id = id,
    title = title,
    description = description,
    isCompleted = isCompleted,
    priority = priority,
    timestamp = timestamp
)

fun Todo.toEntity() = TodoEntity(
    id = id,
    title = title,
    description = description,
    isCompleted = isCompleted,
    priority = priority,
    timestamp = timestamp
)
