package com.android.taskstimer.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "timers")
data class Timer(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val name: String,
    val presetTime: String,
    val remainingTime: String = presetTime
)