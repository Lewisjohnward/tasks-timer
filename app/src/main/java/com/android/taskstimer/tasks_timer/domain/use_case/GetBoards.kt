package com.android.taskstimer.tasks_timer.domain.use_case

import com.android.taskstimer.core.domain.model.BoardItem
import com.android.taskstimer.core.domain.repository.BoardsRepository

class GetBoards(
    private val boardsRepository: BoardsRepository
) {
    suspend operator fun invoke(): List<BoardItem> {
        return boardsRepository.getAllBoards()
    }
}