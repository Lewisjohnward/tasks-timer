package com.android.taskstimer.core.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import com.android.taskstimer.core.data.local.board.BoardDao
import com.android.taskstimer.core.data.local.board.BoardEntity
import com.android.taskstimer.core.data.local.timer.TimerDao
import com.android.taskstimer.core.data.local.timer.TimerEntity
import dagger.Lazy
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject


@Database(
    entities = [TimerEntity::class, BoardEntity::class],
    version = 1,
    exportSchema = false
)
abstract class TasksTimerDatabase : RoomDatabase() {
    abstract val timerDao: TimerDao
    abstract val boardDao: BoardDao

    class Callback @Inject constructor(
        private val dbLazy: Lazy<TasksTimerDatabase>,
    ) : RoomDatabase.Callback() {
        override fun onCreate(db: SupportSQLiteDatabase) {
            super.onCreate(db)


            val timerDao = dbLazy.get().timerDao
            val boardDao = dbLazy.get().boardDao
            CoroutineScope(Dispatchers.IO).launch {
                timerDao.insert(
                    TimerEntity(
                        id = 1,
                        boardId = 1,
                        name = "Clean the floor",
                        presetTime = "120",
                        remainingTime = "120"
                    )
                )
                timerDao.insert(
                    TimerEntity(
                        id = 2,
                        boardId = 1,
                        name = "Unload the washing machine",
                        presetTime = "120",
                        remainingTime = "120"
                    )
                )
                boardDao.insert(
                    BoardEntity(
                        id = 1,
                        name = "Household Chores"
                    )
                )
                timerDao.insert(
                    TimerEntity(
                        boardId = 2,
                        name = "Teach somersault",
                        presetTime = "120",
                        remainingTime = "120"
                    )
                )
                timerDao.insert(
                    TimerEntity(
                        boardId = 2,
                        name = "Teach stand on hind legs",
                        presetTime = "120",
                        remainingTime = "120"
                    )
                )
                boardDao.insert(
                    BoardEntity(
                        id = 2,
                        name = "Train Bruno"
                    )
                )


            }
        }
    }
}