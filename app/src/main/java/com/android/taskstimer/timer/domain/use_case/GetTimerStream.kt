package com.android.taskstimer.timer.domain.use_case

import com.android.taskstimer.core.domain.model.TimerItem
import com.android.taskstimer.core.domain.repository.TimersRepository
import kotlinx.coroutines.flow.Flow

class GetTimerStream(
    private val timersRepository: TimersRepository
) {
    operator fun invoke(timerId: Int): Flow<TimerItem> {
        return timersRepository.getTimerStream(timerId)
    }
}