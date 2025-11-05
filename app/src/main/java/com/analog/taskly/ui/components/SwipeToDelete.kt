package com.analog.taskly.ui.components

import androidx.compose.animation.core.Animatable
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectHorizontalDragGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import com.composables.icons.lucide.Lucide
import com.composables.icons.lucide.Trash2
import kotlinx.coroutines.launch
import kotlin.math.roundToInt

@Composable
fun SwipeToDeleteWrapper(
    onDeleteConfirmed: () -> Unit,
    content: @Composable () -> Unit
) {
    var showDialog by remember { mutableStateOf(false) }
    val offsetX = remember { Animatable(0f) }
    val scope = rememberCoroutineScope()

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(IntrinsicSize.Min)
            .pointerInput(Unit) {
                detectHorizontalDragGestures(
                    onDragEnd = {
                        if (offsetX.value > 200f) { // swipe threshold
                            showDialog = true
                        }
                        scope.launch { offsetX.animateTo(0f) } // reset
                    },
                    onHorizontalDrag = { change, dragAmount ->
                        val newValue = offsetX.value + dragAmount
                        if (newValue >= 0) {
                            scope.launch { offsetX.snapTo(newValue) }
                        }
                        change.consume()
                    }
                )
            }
    ) {
        // Red background fades in with swipe
        val alpha = (offsetX.value / 200f).coerceIn(0f, 1f)
        Row(
            modifier = Modifier
                .fillMaxSize()
                .background(Color(0xFFE53935))
                .padding(start = 16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                imageVector = Lucide.Trash2,
                contentDescription = "Delete",
                tint = Color.White.copy(alpha = alpha),
                modifier = Modifier.size(24.dp)
            )
        }

        // Todo content slides on top
        Box(modifier = Modifier.offset { IntOffset(offsetX.value.roundToInt(), 0) }) {
            content()
        }
    }

    if (showDialog) {
        AlertDialog(
            onDismissRequest = { showDialog = false },
            title = { Text("Delete Task") },
            text = { Text("Do you really want to delete this task? This action cannot be undone.") },
            confirmButton = {
                TextButton(onClick = {
                    onDeleteConfirmed()
                    showDialog = false
                }) { Text("Yes, Delete", color = Color.Red) }
            },
            dismissButton = {
                TextButton(onClick = { showDialog = false }) { Text("Cancel") }
            },
            containerColor = Color.White,
            shape = RoundedCornerShape(8.dp),
            modifier = Modifier.padding(8.dp)
        )
    }
}