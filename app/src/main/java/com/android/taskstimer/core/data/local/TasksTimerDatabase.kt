package com.android.taskstimer.core.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.android.taskstimer.core.data.local.board.BoardDao
import com.android.taskstimer.core.data.local.board.BoardEntity
import com.android.taskstimer.core.data.local.timer.TimerDao
import com.android.taskstimer.core.data.local.timer.TimerEntity


@Database(
     entities = [TimerEntity::class, BoardEntity::class],
     version = 1,
     exportSchema = false
 )
abstract class TasksTimerDatabase: RoomDatabase(){
    abstract val timerDao: TimerDao
    abstract val boardDao: BoardDao
}

//@Database(entities = [Timer::class, Board::class], version = 1, exportSchema = false)
//abstract class TasksTimerDatabase : RoomDatabase() {
//
//    abstract fun timerDao(): TimerDao
//    abstract fun boardDao(): BoardDao
//
//    companion object {
//        @Volatile
//        private var Instance: TasksTimerDatabase? = null
//
//        fun getDatabase(context: Context): TasksTimerDatabase {
//            // if the Instance is not null, return it, otherwise create a new database instance.
//            return Instance ?: synchronized(this) {
//                Room.databaseBuilder(context, TasksTimerDatabase::class.java, "timer_database")
//                    /**
//                     * Setting this option in your app's database builder means that Room
//                     * permanently deletes all data from the tables in your database when it
//                     * attempts to perform a migration with no defined migration path.
//                     */
//                    .fallbackToDestructiveMigration()
//                    .build()
//                    .also { Instance = it }
//            }
//        }
//    }
//}