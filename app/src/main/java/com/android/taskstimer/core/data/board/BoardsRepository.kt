package com.android.taskstimer.core.data.board

import com.android.taskstimer.core.data.timer.Timer
import kotlinx.coroutines.flow.Flow

interface BoardsRepository {
    fun getAllBoardsStream(): Flow<List<Board>>
    suspend fun insertBoard(board: Board)

    fun getBoardsWithTimers(): Flow<List<BoardsWithTimers>>
}