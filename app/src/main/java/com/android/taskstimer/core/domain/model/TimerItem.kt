package com.android.taskstimer.core.domain.model

import androidx.room.ColumnInfo
import androidx.room.PrimaryKey

data class TimerItem(
    val id: Int = 0,
    val boardId: Int,
    val name: String,
    val presetTime: String,
    val remainingTime: String = presetTime
)
