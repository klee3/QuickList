package com.analog.taskly.ui.screens.components

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.DividerDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.analog.taskly.domain.model.Todo
import com.analog.taskly.domain.model.color

@Composable
fun TodoItem(todo: Todo, onToggle: (Todo) -> Unit, modifier: Modifier = Modifier) {
    val textAlpha by animateFloatAsState(targetValue = if (todo.isCompleted) 0.6f else 1f)
    Column(
        modifier = Modifier.clickable {}.background(MaterialTheme.colorScheme.surface)
    ) {


        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = modifier

                .fillMaxWidth()
                .padding(vertical = 8.dp, horizontal = 4.dp)
        ) {
            CircleCheckbox(
                checked = todo.isCompleted,
                checkboxColor = todo.priority.color(),
                modifier = Modifier.clickable(
                    interactionSource = remember { MutableInteractionSource() },
                    indication = null
                ) {
                    onToggle(todo)
                })
            Spacer(modifier = Modifier.padding(4.dp))
            Text(
                text = todo.title, maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                style = TextStyle(
                    fontSize = 16.sp,
                    color = Color.Black.copy(alpha = textAlpha),
                    textDecoration = if (todo.isCompleted) TextDecoration.LineThrough else TextDecoration.None
                ),
            )
        }
    }
}