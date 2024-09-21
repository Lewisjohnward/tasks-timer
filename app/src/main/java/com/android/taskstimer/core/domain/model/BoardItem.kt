package com.android.taskstimer.core.domain.model

import com.android.taskstimer.core.presentation.ui.IconKey

data class BoardItem(
    val id: Int = 1,
    val name: String = "",
    val iconKey: IconKey = IconKey.DEFAULT
)

data class BoardsWithTimersItem(
    val board: BoardItem,
    val timers: List<TimerItem>
)