package com.analog.taskly.ui.screens.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.analog.taskly.domain.model.Todo
import com.analog.taskly.domain.model.TodoSortType
import com.analog.taskly.domain.repository.TodoRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TodoListViewModel @Inject constructor(
    private val repository: TodoRepository
) : ViewModel() {
    var sortType = MutableStateFlow(TodoSortType.TIME)
        private set


    val todos = combine(
        repository.getTodos(), sortType
    ) { todos, sortType ->
        when (sortType) {
            TodoSortType.TIME -> todos.sortedByDescending { it.timestamp }
            TodoSortType.TITLE -> todos.sortedBy { it.title.lowercase() }
            TodoSortType.DONE_STATUS -> todos.sortedBy { it.isCompleted }
            TodoSortType.PRIORITY -> todos.sortedBy { it.priority.ordinal  }
        }
    }.stateIn(
        viewModelScope,
        SharingStarted.WhileSubscribed(),
        emptyList()
    )

    // Set sort type
    fun setSortType(type: TodoSortType) {
    sortType.value = type
    }

    fun addTodo(todo: Todo) {
        viewModelScope.launch {
            repository.insertTodo(todo)
        }
    }

    fun toggleTodoDone(todo: Todo) {
        viewModelScope.launch {
            repository.insertTodo(todo.copy(isCompleted = !todo.isCompleted))
        }
    }

    fun deleteTodo(todo: Todo) {
        viewModelScope.launch {
            repository.deleteTodo(todo)
        }
    }
}
