package com.android.taskstimer.core.data.timer

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "timers")
data class Timer(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    @ColumnInfo(name = "board_id")
    val boardId: Int,
    val name: String,
    val presetTime: String,
    val remainingTime: String = presetTime
)