package com.android.taskstimer.tasks_timer.domain.use_case

import com.android.taskstimer.core.domain.model.BoardItem
import com.android.taskstimer.core.domain.repository.BoardsRepository
import com.android.taskstimer.core.domain.repository.TimersRepository

class DeleteBoard(
    private val boardsRepository: BoardsRepository,
    private val timersRepository: TimersRepository
) {
    suspend operator fun invoke(board: BoardItem){
        boardsRepository.deleteBoard(board)
        timersRepository.deleteAllTimersFromBoard(board.id)
    }
}