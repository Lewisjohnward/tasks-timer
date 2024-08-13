package com.android.taskstimer.edit_timer.domain.use_case

import com.android.taskstimer.core.domain.model.BoardItem
import com.android.taskstimer.core.domain.model.TimerItem
import com.android.taskstimer.core.domain.repository.BoardsRepository
import com.android.taskstimer.core.domain.repository.TimersRepository
import kotlinx.coroutines.flow.first

class AddTimer(
    private val timersRepository: TimersRepository,
    private val boardsRepository: BoardsRepository
) {
    suspend fun invoke(timer: TimerItem){
        if(boardsRepository.getAllBoardsFlow().first().isEmpty()){
            boardsRepository.insertBoard(board = BoardItem(name = "Untitled"))
            val untitledBoard = boardsRepository.getAllBoardsFlow().first()[0]
            timersRepository.insertTimer(timer.copy(boardId = untitledBoard.id))
        }
        timersRepository.insertTimer(timer)
    }
}