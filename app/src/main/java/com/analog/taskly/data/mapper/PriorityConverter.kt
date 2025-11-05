package com.analog.taskly.data.mapper

import androidx.room.TypeConverter
import com.analog.taskly.domain.model.Priority

class PriorityConverter {

    @TypeConverter
    fun fromPriority(priority: Priority): String {
        return priority.name
    }

    @TypeConverter
    fun toPriority(value: String): Priority {
        return Priority.valueOf(value)
    }
}
