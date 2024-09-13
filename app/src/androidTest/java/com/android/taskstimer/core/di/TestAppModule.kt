package com.android.taskstimer.core.di

import android.app.Application
import androidx.room.Room
import com.android.taskstimer.core.data.local.TasksTimerDatabase
import com.android.taskstimer.core.domain.repository.BoardsRepository
import com.android.taskstimer.core.domain.repository.TimersRepository
import com.android.taskstimer.core.repository.FakeBoardsRepositoryImpl
import com.android.taskstimer.core.repository.FakeTimersRepositoryImpl
import com.android.taskstimer.timer.domain.use_case.AddTimer
import com.android.taskstimer.tasks_timer.domain.use_case.DeleteBoard
import com.android.taskstimer.tasks_timer.domain.use_case.DeleteTimer
import com.android.taskstimer.tasks_timer.domain.use_case.GetBoardsFlow
import com.android.taskstimer.tasks_timer.domain.use_case.GetTimersFlow
import com.android.taskstimer.tasks_timer.domain.use_case.InsertBoard
import com.android.taskstimer.tasks_timer.domain.use_case.UpdateTimer
import com.android.taskstimer.timer.domain.use_case.GetTimerStream
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object TestAppModule {

    @Provides
    @Singleton
    fun provideInMemoryDb(application: Application): TasksTimerDatabase {
        return Room.inMemoryDatabaseBuilder(
            application,
            TasksTimerDatabase::class.java
        ).build()
    }

    @Provides
    @Singleton
    fun providesTimersRepository(
        tasksTimerDb: TasksTimerDatabase
    ): TimersRepository {
        return FakeTimersRepositoryImpl(tasksTimerDb)
    }

    @Provides
    @Singleton
    fun provideBoardsRepository(
        tasksTimerDb: TasksTimerDatabase
    ): BoardsRepository {
        return FakeBoardsRepositoryImpl(tasksTimerDb)
    }

    @Provides
    @Singleton
    fun provideGetBoardsFlow(
        boardsRepository: BoardsRepository
    ): GetBoardsFlow {
        return GetBoardsFlow(boardsRepository)
    }

    @Provides
    @Singleton
    fun provideDeleteBoard(
        boardsRepository: BoardsRepository,
        timersRepository: TimersRepository
    ): DeleteBoard {
        return DeleteBoard(boardsRepository, timersRepository)
    }


    @Provides
    @Singleton
    fun provideGetTimersFlow(
        timersRepository: TimersRepository
    ): GetTimersFlow {
        return GetTimersFlow(timersRepository)
    }

//    @Provides
//    @Singleton
//    fun provideGetTimers(
//        timersRepository: TimersRepository,
//    ): GetTimers {
//        return GetTimers(timersRepository)
//    }

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

    @Provides
    @Singleton
    fun provideDeleteTimer(
        timersRepository: TimersRepository
    ): DeleteTimer {
        return DeleteTimer(timersRepository)
    }

    @Provides
    @Singleton
    fun provideGetTimerStream(
        timersRepository: TimersRepository,
    ): GetTimerStream {
        return GetTimerStream(timersRepository)
    }

}