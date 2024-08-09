package com.android.taskstimer.tasks_timer.domain.use_case

import com.android.taskstimer.core.domain.model.TimerItem
import com.android.taskstimer.core.domain.repository.TimersRepository
import kotlinx.coroutines.flow.Flow

class GetTimersFlow(
    private val timersRepository: TimersRepository,
) {
    operator fun invoke(boardId: Int): Flow<List<TimerItem>> {
        return timersRepository.getTimersFlow(boardId = boardId)
    }
}