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

fun TimerItem.formatTime(): String {
    val seconds = remainingTime.toInt()

    if (seconds == 0) {
        return "00:00"
    } else if (seconds < 60) {
        return if (seconds < 10) ("00:0${seconds}")
        else ("00:${seconds}")
    } else {
        val remainingSeconds = seconds % 60
        val remainingMinutes = (seconds - remainingSeconds) / 60

        val secondsString =
            if (remainingSeconds < 10) ("0$remainingSeconds") else remainingSeconds.toString()
        val minutesString =
            if (remainingMinutes < 10) ("0$remainingMinutes") else remainingMinutes.toString()

        return ("$minutesString:$secondsString")
    }
}

fun TimerItem.resetTimer(): TimerItem {
    return this.copy(remainingTime = presetTime)
}