package com.android.taskstimer.tasks_timer.domain.use_case

import com.android.taskstimer.core.data.local.board.Board
import com.android.taskstimer.core.domain.repository.BoardsRepository

class InsertBoard(
    private val boardsRepository: BoardsRepository
) {
    suspend fun invoke(board: Board){
        boardsRepository.insertBoard(board)
    }
}