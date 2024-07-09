package com.android.taskstimer.data.board

import kotlinx.coroutines.flow.Flow

class OfflineBoardsRepository(private val boardDao: BoardDao): BoardsRepository {
    override fun getAllBoardsStream(): Flow<List<Board>> = boardDao.getAllBoards()
    override suspend fun insertBoard(board: Board) = boardDao.insert(board)
}