package com.android.taskstimer.core.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.android.taskstimer.core.data.board.Board
import com.android.taskstimer.core.data.board.BoardDao
import com.android.taskstimer.core.data.timer.Timer
import com.android.taskstimer.core.data.timer.TimerDao

@Database(entities = [Timer::class, Board::class], version = 1, exportSchema = false)
abstract class TasksTimerDatabase : RoomDatabase() {

    abstract fun timerDao(): TimerDao
    abstract fun boardDao(): BoardDao

    companion object {
        @Volatile
        private var Instance: TasksTimerDatabase? = null

        fun getDatabase(context: Context): TasksTimerDatabase {
            // if the Instance is not null, return it, otherwise create a new database instance.
            return Instance ?: synchronized(this) {
                Room.databaseBuilder(context, TasksTimerDatabase::class.java, "timer_database")
                    /**
                     * Setting this option in your app's database builder means that Room
                     * permanently deletes all data from the tables in your database when it
                     * attempts to perform a migration with no defined migration path.
                     */
                    .fallbackToDestructiveMigration()
                    .build()
                    .also { Instance = it }
            }
        }
    }
}