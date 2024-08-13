package com.android.taskstimer.tasks_timer.domain.use_case

import com.android.taskstimer.core.domain.model.BoardItem
import com.android.taskstimer.core.domain.repository.BoardsRepository
import kotlinx.coroutines.flow.Flow

class GetBoardsFlow(
    private val boardsRepository: BoardsRepository
) {
    operator fun invoke(): Flow<List<BoardItem>> {
        return boardsRepository.getAllBoardsFlow()
    }
}