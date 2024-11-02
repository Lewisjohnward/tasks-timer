package com.android.taskstimer.core.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import com.android.taskstimer.core.data.local.board.BoardDao
import com.android.taskstimer.core.data.local.board.BoardEntity
import com.android.taskstimer.core.data.local.timer.TimerDao
import com.android.taskstimer.core.data.local.timer.TimerEntity
import com.android.taskstimer.core.presentation.ui.IconKey
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
                        presetTime = "600",
                        remainingTime = "600"
                    )
                )
                timerDao.insert(
                    TimerEntity(
                        id = 2,
                        boardId = 1,
                        name = "Unload the washing machine",
                        presetTime = "300",
                        remainingTime = "300"
                    )
                )
                boardDao.insert(
                    BoardEntity(
                        id = 1,
                        name = "Household Chores",
                        iconKey = IconKey.CLEANING,
                        timerCount = 2,
                        totalSeconds = 900
                    )
                )
                timerDao.insert(
                    TimerEntity(
                        boardId = 2,
                        name = "Teach shake",
                        presetTime = "450",
                        remainingTime = "450"
                    )
                )
                timerDao.insert(
                    TimerEntity(
                        boardId = 2,
                        name = "Teach somersault",
                        presetTime = "1200",
                        remainingTime = "1200"
                    )
                )
                timerDao.insert(
                    TimerEntity(
                        boardId = 2,
                        name = "Teach stand on hind legs",
                        presetTime = "300",
                        remainingTime = "300"
                    )
                )
                timerDao.insert(
                    TimerEntity(
                        boardId = 2,
                        name = "Teach to sing",
                        presetTime = "3900",
                        remainingTime = "3900"
                    )
                )
                timerDao.insert(
                    TimerEntity(
                        boardId = 2,
                        name = "Teach spin",
                        presetTime = "65",
                        remainingTime = "65"
                    )
                )
                boardDao.insert(
                    BoardEntity(
                        id = 2,
                        name = "Train Bruno",
                        iconKey = IconKey.PETS,
                        timerCount = 5,
                        totalSeconds = 5915
                    )
                )
                boardDao.insert(
                    BoardEntity(
                        id = 3,
                        name = "Language Learning",
                        iconKey = IconKey.BOOK,
                        timerCount = 4,
                        totalSeconds = 9900
                    )
                )
                timerDao.insert(
                    TimerEntity(
                        boardId = 3,
                        name = "Speechling",
                        presetTime = "1200",
                        remainingTime = "1200"
                    )
                )
                timerDao.insert(
                    TimerEntity(
                        boardId = 3,
                        name = "Numbers",
                        presetTime = "300",
                        remainingTime = "300"
                    )
                )
                timerDao.insert(
                    TimerEntity(
                        boardId = 3,
                        name = "Anki",
                        presetTime = "1200",
                        remainingTime = "1200"
                    )
                )
                timerDao.insert(
                    TimerEntity(
                        boardId = 3,
                        name = "Reading",
                        presetTime = "7200",
                        remainingTime = "7200"
                    )
                )
            }
        }
    }
}