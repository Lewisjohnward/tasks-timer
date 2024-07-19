package com.android.taskstimer.core.data.repository

import com.android.taskstimer.core.data.local.TasksTimerDatabase
import com.android.taskstimer.core.data.local.board.Board
import com.android.taskstimer.core.data.local.board.BoardDao
import com.android.taskstimer.core.data.local.board.BoardsWithTimers
import com.android.taskstimer.core.domain.repository.BoardsRepository
import kotlinx.coroutines.flow.Flow

class BoardsRepositoryImpl(
    private val tasksTimerDb: TasksTimerDatabase
) : BoardsRepository {

    private val boardDao = tasksTimerDb.boardDao
    override fun getAllBoardsStream(): Flow<List<Board>> = boardDao.getAllBoards()
    override suspend fun insertBoard(board: Board) = boardDao.insert(board)

    override fun getBoardsWithTimers(): Flow<List<BoardsWithTimers>> = boardDao.getBoardsWithTimers()
}