package com.analog.taskly.ui.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.analog.taskly.domain.model.Priority
import com.analog.taskly.domain.model.Todo
import com.composables.icons.lucide.Lucide
import com.composables.icons.lucide.SendHorizontal
import kotlinx.coroutines.launch


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddTodoBottomSheet(
    showSheet: Boolean,
    onDismiss: () -> Unit,
    onTodoAdded: (Todo) -> Unit
) {
    val sheetState = rememberModalBottomSheetState()
    val scope = rememberCoroutineScope()

    // Animate showing the sheet when showSheet becomes true
    LaunchedEffect(showSheet) {
        if (showSheet) sheetState.show()
        else sheetState.hide()
    }

    // Data to store
    var title by remember { mutableStateOf("") }
    var description by remember { mutableStateOf("") }
    var selectedPriority by remember { mutableStateOf(Priority.Default) }

    if (showSheet || sheetState.isVisible) {
        ModalBottomSheet(
            sheetState = sheetState,
            onDismissRequest = {
                scope.launch {
                    sheetState.hide()
                    onDismiss()
                }
            }
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp)
            ) {
                // --- Title ---
                CustomTextFieldLibrary(
                    value = title,
                    onValueChange = { title = it },
                    placeholder = "What do you need to do?",
                    height = 40.dp, // smaller height
                )

                // --- Description (Optional) ---
                CustomTextFieldLibrary(
                    value = description,
                    onValueChange = { description = it },
                    placeholder = "Description (Optional)",
                    height = 30.dp, // smaller height
                )

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 8.dp), // breathing space
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.End
                ) {
                    // --- Priority Selector ---
                    PrioritySelector(
                        selectedPriority = selectedPriority,
                        onPrioritySelected = { selectedPriority = it },
                        modifier = Modifier.padding(end = 16.dp) // spacing between priority and icon
                    )

                    // --- Done Button ---
                    Box(
                        modifier = Modifier
                            .size(48.dp) // bigger clickable area
                            .clip(CircleShape)
                            .clickable(enabled = title.isNotBlank()) {
                                if (title.isNotBlank()) {
                                    val todo = Todo(
                                        title = title,
                                        description = description.ifBlank { null },
                                        priority = selectedPriority
                                    )
                                    onTodoAdded(todo)

                                    // Reset field data
                                    title = ""
                                    description = ""
                                    selectedPriority = Priority.Default

                                    scope.launch {
                                        sheetState.hide()
                                        onDismiss()
                                    }
                                }
                            },
                        contentAlignment = Alignment.Center,
                    ) {
                        Icon(
                            imageVector = Lucide.SendHorizontal,
                            contentDescription = "Create Task",
                            modifier = Modifier.size(20.dp), // icon size
                            tint = if (title.isNotBlank()) MaterialTheme.colorScheme.primary else Color.LightGray // icon color
                        )
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))
            }
        }
    }
}

