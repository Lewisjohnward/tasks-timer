package com.android.taskstimer.tasks_timer.domain.use_case

import com.android.taskstimer.core.domain.model.TimerItem
import com.android.taskstimer.core.domain.repository.TimersRepository

class DeleteTimer(
    private val timersRepository: TimersRepository
) {
    suspend fun invoke(timer: TimerItem){
        timersRepository.deleteTimer(timer)
    }
}