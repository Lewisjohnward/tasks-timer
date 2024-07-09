package com.android.taskstimer.data.timer

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.android.taskstimer.data.timer.Timer
import kotlinx.coroutines.flow.Flow

@Dao
interface TimerDao {

    @Query("SELECT * from timers ORDER BY id ASC")
    fun getAllTimers(): Flow<List<Timer>>

    @Query("SELECT * from timers WHERE id = :id")
    fun getTimer(id: Int): Flow<Timer>

    // Specify the conflict strategy as IGNORE, when the user tries to add an
    // existing Item into the database Room ignores the conflict.
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(timer: Timer)

    @Update
    suspend fun update(timer: Timer)

    @Delete
    suspend fun delete(timer: Timer)
}
