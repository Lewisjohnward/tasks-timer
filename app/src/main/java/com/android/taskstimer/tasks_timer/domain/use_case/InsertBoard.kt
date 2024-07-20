package com.android.taskstimer.tasks_timer.domain.use_case

import com.android.taskstimer.core.domain.model.BoardItem
import com.android.taskstimer.core.domain.repository.BoardsRepository

class InsertBoard(
    private val boardsRepository: BoardsRepository
) {
    suspend fun invoke(board: BoardItem){
        boardsRepository.insertBoard(board)
    }
}