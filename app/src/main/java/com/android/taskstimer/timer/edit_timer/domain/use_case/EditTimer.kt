package com.android.taskstimer.timer.edit_timer.domain.use_case

import com.android.taskstimer.core.domain.model.TimerItem
import com.android.taskstimer.core.domain.repository.TimersRepository
import kotlinx.coroutines.flow.Flow

class EditTimer(
    private val timersRepository: TimersRepository
) {

    suspend operator fun invoke(timer: TimerItem){
        timersRepository.updateTimer(timer)
    }
}