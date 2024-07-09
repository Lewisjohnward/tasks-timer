package com.android.taskstimer.data.board

import kotlinx.coroutines.flow.Flow

interface BoardsRepository {
    fun getAllBoardsStream(): Flow<List<Board>>
    suspend fun insertBoard(board: Board)
}