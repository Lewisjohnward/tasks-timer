package com.android.taskstimer.tasks_timer.domain.use_case

import com.android.taskstimer.core.data.local.board.BoardsWithTimers
import com.android.taskstimer.core.domain.repository.BoardsRepository
import kotlinx.coroutines.flow.Flow

class GetAllBoardsWithTimers(
    private val boardsRepository: BoardsRepository
) {
    fun invoke(): Flow<List<BoardsWithTimers>> {
        return boardsRepository.getBoardsWithTimers()
    }
}