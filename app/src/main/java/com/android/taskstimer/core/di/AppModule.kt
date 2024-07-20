package com.android.taskstimer.core.di

import android.app.Application
import androidx.room.Room
import com.android.taskstimer.core.data.local.TasksTimerDatabase
import com.android.taskstimer.core.data.repository.BoardsRepositoryImpl
import com.android.taskstimer.core.data.repository.TimersRepositoryImpl
import com.android.taskstimer.core.domain.repository.BoardsRepository
import com.android.taskstimer.core.domain.repository.TimersRepository
import com.android.taskstimer.edit_timer.domain.use_case.AddTimer
import com.android.taskstimer.tasks_timer.domain.use_case.GetAllBoardsWithTimers
import com.android.taskstimer.tasks_timer.domain.use_case.InsertBoard
import com.android.taskstimer.tasks_timer.domain.use_case.UpdateTimer
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

    @Provides
    @Singleton
    fun provideGetAllBoardsUseCase(
        boardsRepository: BoardsRepository
    ): GetAllBoardsWithTimers {
        return GetAllBoardsWithTimers(boardsRepository)
    }

    @Provides
    @Singleton
    fun provideInsertBoard(
        boardsRepository: BoardsRepository
    ): InsertBoard {
        return InsertBoard(boardsRepository)
    }


    @Provides
    @Singleton
    fun provideUpdateTimer(
        timersRepository: TimersRepository
    ): UpdateTimer {
        return UpdateTimer(timersRepository)
    }

    @Provides
    @Singleton
    fun provideAddTimer(
        timersRepository: TimersRepository,
        boardsRepository: BoardsRepository
    ): AddTimer {
        return AddTimer(timersRepository, boardsRepository)
    }
}