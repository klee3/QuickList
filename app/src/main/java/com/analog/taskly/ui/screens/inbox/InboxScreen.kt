package com.analog.taskly.ui.screens.inbox

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.analog.taskly.domain.model.TodoSortType
import com.analog.taskly.domain.model.TopBarAction
import com.analog.taskly.ui.components.AddTodoBottomSheet
import com.analog.taskly.ui.components.AddTodoFabWrapper
import com.analog.taskly.ui.components.AppTopBar
import com.analog.taskly.ui.components.SwipeToDeleteWrapper
import com.analog.taskly.ui.screens.components.TodoItem
import com.analog.taskly.ui.screens.viewmodel.TodoListViewModel
import com.composables.icons.lucide.ArrowDownWideNarrow
import com.composables.icons.lucide.Lucide

@Composable
fun InboxScreen(viewModel: TodoListViewModel = hiltViewModel()) {
    var showSheet by remember { mutableStateOf(false) }
    val todos by viewModel.todos.collectAsState()


    // Sort Menu
    var sortMenuExpanded by remember { mutableStateOf(false) }

    val topBarAction = listOf(
        TopBarAction(
            icon = Lucide.ArrowDownWideNarrow,
            contentDescription = "Sort By",
            onClick = { sortMenuExpanded = true },
            content = {
                DropdownMenu(
                    expanded = sortMenuExpanded,
                    onDismissRequest = { sortMenuExpanded = false }
                ) {
                    // Dropdown title
                    Text(
                        text = "SORT BY".uppercase(),
                        style = MaterialTheme.typography.labelSmall.copy(
                            fontWeight = FontWeight.Medium,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        ),
                        modifier = Modifier
                            .padding(horizontal = 16.dp)
                    )

                    DropdownMenuItem(
                        text = { Text("Priority") },
                        onClick = {
                            viewModel.setSortType(TodoSortType.PRIORITY)
                            sortMenuExpanded = false
                        }
                    )

                    DropdownMenuItem(
                        text = { Text("Time") },
                        onClick = {
                            viewModel.setSortType(TodoSortType.TIME)
                            sortMenuExpanded = false
                        }
                    )

                    DropdownMenuItem(
                        text = { Text("Name") },
                        onClick = {
                            viewModel.setSortType(TodoSortType.TITLE)
                            sortMenuExpanded = false
                        }
                    )

                    DropdownMenuItem(
                        text = { Text("Completed") },
                        onClick = {
                            viewModel.setSortType(TodoSortType.DONE_STATUS)
                            sortMenuExpanded = false
                        }
                    )
                }
            }
        )
    )

    AddTodoFabWrapper(
        fabIcon = Icons.Default.Add,
        fabText = "Add",
        onFabClick = { showSheet = true }
    ) {
        Column {
            AppTopBar(title = "Inbox", actions = topBarAction)

            if (todos.isEmpty()) {
                Box(
                    modifier = Modifier
                        .fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "Nothing left to do — relax and enjoy your day",
                        style = MaterialTheme.typography.bodyLarge,
                        textAlign = TextAlign.Center,
                        color = Color.Gray
                    )
                }
            }

            LazyColumn {
                items(todos) { todo ->
                    SwipeToDeleteWrapper(
                        onDeleteConfirmed = {
                            // Remove the item from your list or database
                            viewModel.deleteTodo(todo)
                        }
                    ) {

                        TodoItem(
                            todo = todo,
                            onToggle = { viewModel.toggleTodoDone(todo) },
                            modifier = Modifier.padding(8.dp)
                        )
                    }

                }
            }

            AddTodoBottomSheet(
                showSheet,
                onDismiss = { showSheet = false },
                onTodoAdded = { viewModel.addTodo(it) })


        }
    }
}