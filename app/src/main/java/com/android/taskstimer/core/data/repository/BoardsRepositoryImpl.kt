package com.android.taskstimer.core.data.repository

import com.android.taskstimer.core.data.local.TasksTimerDatabase
import com.android.taskstimer.core.data.mapper.toBoardEntityForInsert
import com.android.taskstimer.core.data.mapper.toBoardItem
import com.android.taskstimer.core.data.mapper.toBoardsWithTimersItem
import com.android.taskstimer.core.domain.model.BoardItem
import com.android.taskstimer.core.domain.model.BoardsWithTimersItem
import com.android.taskstimer.core.domain.repository.BoardsRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class BoardsRepositoryImpl(
    private val tasksTimerDb: TasksTimerDatabase
) : BoardsRepository {

    private val boardDao = tasksTimerDb.boardDao
    override fun getAllBoardsStream(): Flow<List<BoardItem>> =
        boardDao.getAllBoards().map { it -> it.map { it.toBoardItem() } }

    override suspend fun insertBoard(board: BoardItem) =
        boardDao.insert(board.toBoardEntityForInsert())

    override fun getBoardsWithTimers(): Flow<List<BoardsWithTimersItem>> =
        boardDao.getBoardsWithTimers().map { it ->
            it.map {
                it.toBoardsWithTimersItem()
            }
        }
}