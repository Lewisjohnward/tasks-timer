package com.android.taskstimer.timer.domain.use_case

import com.android.taskstimer.core.domain.model.BoardItem
import com.android.taskstimer.core.domain.model.TimerItem
import com.android.taskstimer.core.domain.repository.BoardsRepository
import com.android.taskstimer.core.domain.repository.TimersRepository
import kotlinx.coroutines.ExecutorCoroutineDispatcher
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.withContext


class AddTimer(
    private val timersRepository: TimersRepository,
    private val boardsRepository: BoardsRepository,
    private val singleThreadDispatcher: ExecutorCoroutineDispatcher
) {
    suspend fun invoke(timer: TimerItem) {
        withContext(singleThreadDispatcher) {
            val boards = boardsRepository.getAllBoardsFlow()
            if (boards.first().isEmpty()) {
                boardsRepository.insertBoard(
                    board = BoardItem(
                        name = "Untitled",
                        totalSeconds = timer.presetTime.toInt(),
                        timerCount = 1
                    )
                )
                val untitledBoard = boards.first()[0]
                timersRepository.insertTimer(timer.copy(boardId = untitledBoard.id))
            } else {
                val board = boardsRepository.getBoard(timer.boardId)

                boardsRepository.insertBoard(
                    board.copy(
                        totalSeconds = board.totalSeconds + timer.presetTime.toInt(),
                        timerCount = board.timerCount + 1
                    )
                )
                timersRepository.insertTimer(timer)
            }
        }
    }
}