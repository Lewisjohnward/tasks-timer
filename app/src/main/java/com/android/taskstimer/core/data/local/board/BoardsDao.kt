package com.android.taskstimer.core.data.local.board

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface BoardDao {

    @Query("SELECT * from boards ORDER BY id ASC")
    fun getAllBoardsFlow(): Flow<List<BoardEntity>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(board: BoardEntity)

    @Delete
    suspend fun delete(board: BoardEntity)
}