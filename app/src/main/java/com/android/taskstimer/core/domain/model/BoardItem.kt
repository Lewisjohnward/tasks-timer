package com.android.taskstimer.core.domain.model

import com.android.taskstimer.core.presentation.ui.IconKey

data class BoardItem(
    val id: Int = 1,
    val name: String = "",
    val timerCount: Int = 0,
    val totalSeconds: Int = 0,
    val iconKey: IconKey = IconKey.DEFAULT,
    val selected: Boolean = false
)

data class BoardsWithTimersItem(
    val board: BoardItem,
    val timers: List<TimerItem>
)