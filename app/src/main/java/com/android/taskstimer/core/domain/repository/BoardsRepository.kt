package com.android.taskstimer.core.domain.repository

import com.android.taskstimer.core.domain.model.BoardItem
import com.android.taskstimer.core.domain.model.BoardsWithTimersItem
import kotlinx.coroutines.flow.Flow

interface BoardsRepository {
    suspend fun getAllBoards(): List<BoardItem>
    suspend fun insertBoard(board: BoardItem)
}