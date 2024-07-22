package com.android.taskstimer.tasks_timer.domain.use_case

import com.android.taskstimer.core.domain.model.TimerItem
import com.android.taskstimer.core.domain.repository.TimersRepository

class GetTimers(
    private val timersRepository: TimersRepository,
) {
    suspend operator fun invoke(boardId: Int): List<TimerItem> {
        return timersRepository.getTimers(boardId = boardId)
    }


}