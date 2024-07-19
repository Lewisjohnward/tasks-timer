package com.android.taskstimer.core.data.board

import com.android.taskstimer.core.data.timer.Timer
import kotlinx.coroutines.flow.Flow

class OfflineBoardsRepository(private val boardDao: BoardDao) : BoardsRepository {
    override fun getAllBoardsStream(): Flow<List<Board>> = boardDao.getAllBoards()
    override suspend fun insertBoard(board: Board) = boardDao.insert(board)

    override fun getBoardsWithTimers(): Flow<List<BoardsWithTimers>> = boardDao.getBoardsWithTimers()
}