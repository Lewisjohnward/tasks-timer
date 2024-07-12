package com.android.taskstimer.data.board

import com.android.taskstimer.data.timer.Timer
import kotlinx.coroutines.flow.Flow

class OfflineBoardsRepository(private val boardDao: BoardDao) : BoardsRepository {
    override fun getAllBoardsStream(): Flow<List<Board>> = boardDao.getAllBoards()
    override suspend fun insertBoard(board: Board) = boardDao.insert(board)

    override fun getBoardsWithTimers(boardName: String): Flow<List<Timer>> = boardDao.getBoardsWithTimers(boardName)
}