package com.android.taskstimer.core.di

import android.app.Application
import androidx.room.Room
import com.android.taskstimer.core.data.local.TasksTimerDatabase
import com.android.taskstimer.core.data.repository.BoardsRepositoryImpl
import com.android.taskstimer.core.data.repository.TimersRepositoryImpl
import com.android.taskstimer.core.domain.repository.BoardsRepository
import com.android.taskstimer.core.domain.repository.TimersRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideTasksTimerDb(application: Application): TasksTimerDatabase {
        return Room.databaseBuilder(
            application,
            TasksTimerDatabase::class.java,
            "timer_database"
        ).build()
    }

    @Provides
    @Singleton
    fun providesTimersRepository(
        tasksTimerDb: TasksTimerDatabase
    ): TimersRepository{
        return TimersRepositoryImpl(tasksTimerDb)
    }

    @Provides
    @Singleton
    fun provideBoardsRepository(
        tasksTimerDb: TasksTimerDatabase
    ): BoardsRepository{
        return BoardsRepositoryImpl(tasksTimerDb)
    }
}