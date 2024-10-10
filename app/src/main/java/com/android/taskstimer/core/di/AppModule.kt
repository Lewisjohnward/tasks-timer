package com.android.taskstimer.core.di

import android.app.Application
import android.content.Context
import androidx.room.Room
import com.android.taskstimer._other.service.TasksTimerServiceManager
import com.android.taskstimer._other.service.TasksTimerServiceManagerImpl
import com.android.taskstimer.core.data.local.TasksTimerDatabase
import com.android.taskstimer.core.data.repository.BoardsRepositoryImpl
import com.android.taskstimer.core.data.repository.TimersRepositoryImpl
import com.android.taskstimer.core.domain.repository.BoardsRepository
import com.android.taskstimer.core.domain.repository.TimersRepository
import com.android.taskstimer.tasks_timer.domain.use_case.DeleteBoard
import com.android.taskstimer.tasks_timer.domain.use_case.DeleteTimer
import com.android.taskstimer.tasks_timer.domain.use_case.GetBoardsFlow
import com.android.taskstimer.tasks_timer.domain.use_case.GetTimersFlow
import com.android.taskstimer.tasks_timer.domain.use_case.InsertBoard
import com.android.taskstimer.tasks_timer.domain.use_case.UpdateTimer
import com.android.taskstimer.timer.domain.use_case.AddTimer
import com.android.taskstimer.timer.domain.use_case.GetTimerStream
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExecutorCoroutineDispatcher
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.asCoroutineDispatcher
import java.util.concurrent.Executors
import javax.inject.Qualifier
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
class AppModule {


    @Singleton
    @Provides
    fun providesCoroutineScope(): CoroutineScope {
        return CoroutineScope(SupervisorJob() + Dispatchers.Default)
    }

    @Provides
    @Singleton
    fun provideTasksTimerDb(
        application: Application,
        callback: TasksTimerDatabase.Callback
    ): TasksTimerDatabase {
        return Room.databaseBuilder(
            application,
            TasksTimerDatabase::class.java,
            "timer_database"
        )
            .allowMainThreadQueries()
            .addCallback(callback)
            .build()
    }

    @Provides
    @Singleton
    fun providesTimersRepository(
        tasksTimerDb: TasksTimerDatabase
    ): TimersRepository {
        return TimersRepositoryImpl(tasksTimerDb)
    }

    @Provides
    @Singleton
    fun provideBoardsRepository(
        tasksTimerDb: TasksTimerDatabase
    ): BoardsRepository {
        return BoardsRepositoryImpl(tasksTimerDb)
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
        boardsRepository: BoardsRepository,
        executorCoroutineDispatcher: ExecutorCoroutineDispatcher
    ): AddTimer {
        return AddTimer(timersRepository, boardsRepository, executorCoroutineDispatcher)
    }

    @Provides
    @Singleton
    fun provideDeleteTimer(
        timersRepository: TimersRepository,
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

    @Provides
    @Singleton
    fun provideTasksTimerServiceManager(
        @ApplicationContext applicationContext: Context,
    ): TasksTimerServiceManager {
        return TasksTimerServiceManagerImpl(applicationContext)
    }


    @ApplicationScope
    @Singleton
    @Provides
    fun provideApplicationScope(): CoroutineScope = CoroutineScope(SupervisorJob())

    @Provides
    @Singleton
    fun provideSingleThreadDispatcher(): ExecutorCoroutineDispatcher =
        Executors.newSingleThreadExecutor().asCoroutineDispatcher()
}

@Retention(AnnotationRetention.RUNTIME)
@Qualifier
annotation class ApplicationScope
