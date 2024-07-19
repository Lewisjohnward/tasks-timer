package com.android.taskstimer.core.domain.repository

import com.android.taskstimer.core.data.local.board.Board
import com.android.taskstimer.core.data.local.board.BoardsWithTimers
import kotlinx.coroutines.flow.Flow

interface BoardsRepository {
    fun getAllBoardsStream(): Flow<List<Board>>
    suspend fun insertBoard(board: Board)

    fun getBoardsWithTimers(): Flow<List<BoardsWithTimers>>
}