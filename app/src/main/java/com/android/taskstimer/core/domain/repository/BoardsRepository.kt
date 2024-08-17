package com.android.taskstimer.core.domain.repository

import com.android.taskstimer.core.domain.model.BoardItem
import kotlinx.coroutines.flow.Flow

interface BoardsRepository {
    fun getAllBoardsFlow(): Flow<List<BoardItem>>

    suspend fun getInitBoard(): BoardItem?

    suspend fun getBoard(boardId: Int): BoardItem

    suspend fun insertBoard(board: BoardItem)

    suspend fun deleteBoard(board: BoardItem)
}