package com.analog.taskly.di

import android.content.Context
import androidx.room.Room
import com.analog.taskly.data.local.TodoDatabase
import com.analog.taskly.data.repository.TodoRepositoryImpl
import com.analog.taskly.domain.repository.TodoRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {
    @Provides
    @Singleton
    fun provideTodoDatabase(
        @ApplicationContext context: Context
    ) = Room.databaseBuilder(
        context, TodoDatabase::class.java, TodoDatabase.DATABASE_NAME
    ).fallbackToDestructiveMigration(true).build()


    @Provides
    @Singleton
    fun provideTodoRepository(db: TodoDatabase): TodoRepository = TodoRepositoryImpl(db.todoDao)
}