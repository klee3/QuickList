package com.analog.taskly.ui.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import com.analog.taskly.domain.model.Priority
import com.analog.taskly.domain.model.color
import com.analog.taskly.domain.model.displayName
import com.composables.icons.lucide.Flag
import com.composables.icons.lucide.Lucide

@Composable
fun PrioritySelector(
    selectedPriority: Priority,
    onPrioritySelected: (Priority) -> Unit,
    modifier: Modifier = Modifier
) {
    var expanded by remember { mutableStateOf(false) }

    Box(
        modifier = modifier
            .size(48.dp) // bigger click area
            .clip(CircleShape)
            .clickable { expanded = true },
        contentAlignment = Alignment.Center
    ) {
        // Selected priority button
        Icon(
            imageVector = Lucide.Flag,
            contentDescription = selectedPriority.displayName(),
            modifier = Modifier.size(20.dp),
            tint = selectedPriority.color()
        )


        // Dropdown menu
        DropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false },
            modifier = Modifier.padding(end = 12.dp)
        ) {
            Priority.entries.forEach { priority ->
                DropdownMenuItem(
                    text = {
                        Row(
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Icon(
                                imageVector = Lucide.Flag,
                                contentDescription = priority.displayName(),
                                modifier = Modifier.size(20.dp),
                                tint = priority.color()
                            )
                            Spacer(modifier = Modifier.width(8.dp))
                            Text(priority.displayName())
                        }
                    },
                    onClick = {
                        onPrioritySelected(priority)
                        expanded = false
                    },
                    enabled = true, // optional, default is true
                    interactionSource = remember { MutableInteractionSource() } // must be a proper object
                )
            }
        }
    }
}