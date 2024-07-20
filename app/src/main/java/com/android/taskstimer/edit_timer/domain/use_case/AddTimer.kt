package com.android.taskstimer.edit_timer.domain.use_case

import com.android.taskstimer.core.domain.model.BoardItem
import com.android.taskstimer.core.domain.model.TimerItem
import com.android.taskstimer.core.domain.repository.BoardsRepository
import com.android.taskstimer.core.domain.repository.TimersRepository
import kotlinx.coroutines.flow.map

class AddTimer(
    private val timersRepository: TimersRepository,
    private val boardsRepository: BoardsRepository
) {
    suspend fun invoke(timer: TimerItem){
        timersRepository.insertTimer(timer)
    }
}