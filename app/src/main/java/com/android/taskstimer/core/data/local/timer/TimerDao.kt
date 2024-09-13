package com.android.taskstimer.core.data.local.timer

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

@Dao
interface TimerDao {

    @Query("SELECT * from timers ORDER BY id ASC")
    fun getAllTimers(): Flow<List<TimerEntity>>

    @Query("SELECT * from timers WHERE board_id = :boardId")
    fun getTimersFlow(boardId: Int): Flow<List<TimerEntity>>

    @Query("SELECT * from timers WHERE board_id = :boardId")
    suspend fun getTimers(boardId: Int): List<TimerEntity>

    @Query("SELECT * from timers WHERE id = :timerId")
    fun getTimerStream(timerId: Int): Flow<TimerEntity>

    // Specify the conflict strategy as IGNORE, when the user tries to add an
    // existing Item into the database Room ignores the conflict.
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(timer: TimerEntity)

    @Update
    suspend fun update(timer: TimerEntity)

    @Update
    suspend fun updateTimers(timers: List<TimerEntity>)

    @Delete
    suspend fun delete(timer: TimerEntity)

    @Query("DELETE from timers WHERE board_id = :boardId")
    suspend fun deleteAllTimersFromBoard(boardId: Int)

}
