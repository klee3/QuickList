package com.analog.taskly.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.analog.taskly.data.local.dao.TodoDao
import com.analog.taskly.data.local.entities.TodoEntity
import com.analog.taskly.data.mapper.PriorityConverter

@Database(entities = [TodoEntity::class], version = 2)
@TypeConverters(PriorityConverter::class)
abstract class TodoDatabase: RoomDatabase() {
    abstract val todoDao: TodoDao

    companion object {
        const val DATABASE_NAME = "todo_db"
    }
}