package com.android.taskstimer.core.domain.model

data class BoardItem(
    val id: Int = 0,
    val name: String = "Untitled",
)

data class BoardsWithTimersItem(
    val board: BoardItem,
    val timers: List<TimerItem>
)