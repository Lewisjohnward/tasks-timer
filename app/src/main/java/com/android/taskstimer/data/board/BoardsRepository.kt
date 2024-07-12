package com.android.taskstimer.data.board

import com.android.taskstimer.data.timer.Timer
import kotlinx.coroutines.flow.Flow

interface BoardsRepository {
    fun getAllBoardsStream(): Flow<List<Board>>
    suspend fun insertBoard(board: Board)

    fun getBoardsWithTimers(boardName: String): Flow<List<Timer>>
}