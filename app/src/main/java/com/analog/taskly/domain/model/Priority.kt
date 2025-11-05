package com.analog.taskly.domain.model

import androidx.compose.ui.graphics.Color

enum class Priority(val label: String) {
    CRITICAL("Critical"),
    HIGH("High"),
    MEDIUM("Medium"),
    LOW("Low"),
    NONE("None");

    companion object {
        val Default = NONE
    }
}

fun Priority.color(): Color {
    return when (this) {
        Priority.CRITICAL -> Color(0xFFF44336)
        Priority.HIGH -> Color(0xFFFF9800)
        Priority.MEDIUM -> Color(0xFFFFC107)
        Priority.LOW -> Color(0xFF4CAF50)
        Priority.NONE -> Color(0xFF9E9E9E)
    }
}

fun Priority.displayName(): String {
    return when (this) {
        Priority.CRITICAL -> "Critical"
        Priority.HIGH -> "High"
        Priority.MEDIUM -> "Medium"
        Priority.LOW -> "Low"
        Priority.NONE -> "None"
    }
}