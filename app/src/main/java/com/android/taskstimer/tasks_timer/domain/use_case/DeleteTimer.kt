package com.android.taskstimer.tasks_timer.domain.use_case

import com.android.taskstimer.core.domain.model.TimerItem
import com.android.taskstimer.core.domain.repository.BoardsRepository
import com.android.taskstimer.core.domain.repository.TimersRepository

class DeleteTimer(
    private val timersRepository: TimersRepository,
    private val boardsRepository: BoardsRepository
) {
    suspend fun invoke(timer: TimerItem){
        val board = boardsRepository.getBoard(timer.boardId)

        boardsRepository.insertBoard(
            board.copy(
                totalSeconds = board.totalSeconds - timer.presetTime.toInt(),
                timerCount = board.timerCount - 1
            )
        )
        timersRepository.deleteTimer(timer)
    }
}