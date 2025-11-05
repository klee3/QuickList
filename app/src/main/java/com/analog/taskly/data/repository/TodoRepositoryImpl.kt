package com.analog.taskly.data.repository

import com.analog.taskly.data.local.dao.TodoDao
import com.analog.taskly.data.mapper.toDomain
import com.analog.taskly.data.mapper.toEntity
import com.analog.taskly.domain.model.Todo
import com.analog.taskly.domain.repository.TodoRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class TodoRepositoryImpl (
    private val dao: TodoDao
) : TodoRepository {
    override fun getTodos(): Flow<List<Todo>> =
        dao.getTodos().map { list -> list.map { it.toDomain() } }

    override suspend fun insertTodo(todo: Todo) =
        dao.insertTodo(todo.toEntity())

    override suspend fun deleteTodo(todo: Todo) =
        dao.deleteTodo(todo.toEntity())
}