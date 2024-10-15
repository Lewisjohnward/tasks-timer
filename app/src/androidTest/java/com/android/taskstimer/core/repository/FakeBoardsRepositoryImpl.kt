package com.android.taskstimer.core.repository

import com.android.taskstimer.core.data.local.TasksTimerDatabase
import com.android.taskstimer.core.data.mapper.toBoardEntity
import com.android.taskstimer.core.data.mapper.toBoardItem
import com.android.taskstimer.core.domain.model.BoardItem
import com.android.taskstimer.core.domain.repository.BoardsRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class FakeBoardsRepositoryImpl(
    private val tasksTimerDb: TasksTimerDatabase
) : BoardsRepository {

    private val boardDao = tasksTimerDb.boardDao
    override fun getAllBoardsFlow(): Flow<List<BoardItem>> =
        boardDao.getAllBoardsFlow().map { it -> it.map { it.toBoardItem() } }

    override suspend fun getInitBoard(): BoardItem? {
        return boardDao.getInitBoard()?.toBoardItem()
    }

    override suspend fun getBoard(boardId: Int): BoardItem =
        boardDao.getBoard(boardId).toBoardItem()

    override suspend fun insertBoard(board: BoardItem) =
        boardDao.insert(board.toBoardEntity())

    override suspend fun deleteBoard(board: BoardItem) =
        boardDao.delete(board.toBoardEntity())
}
