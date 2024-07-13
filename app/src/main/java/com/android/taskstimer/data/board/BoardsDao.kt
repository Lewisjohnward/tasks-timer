package com.android.taskstimer.data.board

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import com.android.taskstimer.data.timer.Timer
import kotlinx.coroutines.flow.Flow

@Dao
interface BoardDao {

    @Query("SELECT * from boards ORDER BY id ASC")
    fun getAllBoards(): Flow<List<Board>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(board: Board)

    @Query("SELECT * FROM boards")
    fun getBoardsWithTimers(): Flow<List<BoardsWithTimers>>
}