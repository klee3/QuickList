package com.analog.taskly.ui.screens.home

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectHorizontalDragGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Button
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CheckboxDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.analog.taskly.domain.model.Todo
import com.analog.taskly.ui.screens.components.TodoItem
import com.analog.taskly.ui.screens.viewmodel.TodoListViewModel
import kotlinx.coroutines.launch


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    viewModel: TodoListViewModel = hiltViewModel()
) {
    val todos by viewModel.todos.collectAsState()
    val sortedTodos = todos.sortedBy { it.isCompleted }

    val sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)
    val coroutineScope = rememberCoroutineScope()

    var showSheet by remember { mutableStateOf(false) }
    var newTaskName by remember { mutableStateOf("") }

    // ----- Main UI -----
    Scaffold(
        floatingActionButton = {
            FloatingActionButton(onClick = { showSheet = true }) {
                Icon(Icons.Default.Add, contentDescription = "Add Todo")
            }
        }
    ) { padding ->
        LazyColumn(modifier = Modifier.padding(padding)) {
            items(sortedTodos) { todo ->
               for(i in 1..20) {
                   TodoItemWithAction(
                       todo = todo,
                       onToggle = { viewModel.toggleTodoDone(todo) },
                       onDelete = {
                           viewModel.deleteTodo(todo)
                       })
               }
            }
        }
    }

    // ----- Bottom Sheet -----
    if (showSheet) {
        ModalBottomSheet(
            onDismissRequest = { showSheet = false },
            sheetState = sheetState
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
            ) {
                Text(
                    text = "Add New Task",
                    style = MaterialTheme.typography.titleLarge
                )

                Spacer(modifier = Modifier.height(12.dp))

                OutlinedTextField(
                    value = newTaskName,
                    onValueChange = { newTaskName = it },
                    label = { Text("Task name") },
                    modifier = Modifier.fillMaxWidth(),
                    singleLine = true
                )

                Spacer(modifier = Modifier.height(16.dp))

                Button(
                    onClick = {
                        if (newTaskName.isNotBlank()) {
                            viewModel.addTodo(
                                Todo(title = newTaskName)
                            )
                            newTaskName = ""
                            coroutineScope.launch { sheetState.hide() }
                                .invokeOnCompletion { showSheet = false }
                        }
                    },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text("Add Task")
                }

                Spacer(modifier = Modifier.height(20.dp))
            }
        }
    }
}

@Composable
fun TodoItemWithAction(
    todo: Todo,
    onToggle: (Todo) -> Unit,
    onDelete: (Todo) -> Unit
) {
    val swipeOffset = remember { androidx.compose.runtime.mutableStateOf(0f) }
    val maxSwipe = 120.dp // max swipe distance
    val swipePx = with(LocalDensity.current) { maxSwipe.toPx() }

    var showAction by remember { mutableStateOf(false) }

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color.Transparent)
            .pointerInput(Unit) {
                detectHorizontalDragGestures(
                    onDragEnd = {
                        // Only consider right-to-left swipe
                        if (swipeOffset.value < -swipePx / 2) {
                            swipeOffset.value = -swipePx
                            showAction = true
                        } else {
                            swipeOffset.value = 0f
                            showAction = false
                        }
                    },
                    onHorizontalDrag = { change, dragAmount ->
                        val newOffset = swipeOffset.value + dragAmount
                        // Clamp swipe only for right-to-left (negative values)
                        swipeOffset.value = newOffset.coerceIn(-swipePx, 0f)
                    }
                )
            }
            .clickable {
                // Clicking elsewhere resets the swipe
                swipeOffset.value = 0f
                showAction = false
            }
    ) {
        // Background Delete button
        if (showAction) {
            Box(
                modifier = Modifier
                    .matchParentSize(),
                contentAlignment = Alignment.CenterEnd
            ) {
                TextButton(
                    onClick = {
                        onDelete(todo)
//                        swipeOffset.value = 0f
                        showAction = false
                    },
                    modifier = Modifier.padding(end = 16.dp)
                ) {
                    Icon(
                        imageVector = Icons.Default.Delete,
                        contentDescription = "Delete",
                        tint = Color.Red,
                        modifier = Modifier
                            .padding(end = 16.dp)
                            .size(28.dp)
                    )
                }
            }
        }
        TodoItem(todo, onToggle, modifier = Modifier.offset{ IntOffset(swipeOffset.value.toInt(), 0) })
    }
}

@Composable
@Preview(showBackground = true, showSystemUi = true)
fun HomeScreenPreview() {
    HomeScreen()
}