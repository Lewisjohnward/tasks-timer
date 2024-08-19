package com.android.taskstimer.core.domain.model

data class BoardItem(
    val id: Int = 1,
    val name: String = "",
)

data class BoardsWithTimersItem(
    val board: BoardItem,
    val timers: List<TimerItem>
)