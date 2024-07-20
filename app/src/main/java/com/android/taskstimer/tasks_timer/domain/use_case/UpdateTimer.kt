package com.android.taskstimer.tasks_timer.domain.use_case

import com.android.taskstimer.core.data.local.timer.Timer
import com.android.taskstimer.core.domain.repository.TimersRepository

class UpdateTimer(
    private val timersRepository: TimersRepository
) {
    suspend fun invoke(timer: Timer){
        timersRepository.updateTimer(timer)
    }
}