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