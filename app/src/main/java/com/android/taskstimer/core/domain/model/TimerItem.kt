package com.android.taskstimer.core.domain.model

import java.time.Instant
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import java.util.Locale

data class TimerItem(
    val id: Int = 0,
    val boardId: Int = 0,
    val name: String = "",
    val presetTime: String = "",
    val remainingTime: String = "",
    val lastEndedAt: Long = 0L
)

fun TimerItem.formatTime(): String {
    val seconds = remainingTime.toInt()

    val hours = seconds / 3600
    val minutes = (seconds % 3600) / 60
    val remainingSeconds = seconds % 60

    return if (hours > 0) {
        // Format as "hh:mm:ss"
        String.format("%02d:%02d:%02d", hours, minutes, remainingSeconds)
    } else {
        // Format as "mm:ss"
        String.format("%02d:%02d", minutes, remainingSeconds)
    }
}


fun TimerItem.formatLastEndedAt(): String {
    if (lastEndedAt == 0L) return ""

    val formatter = DateTimeFormatter.ofPattern("dd MMMM (EEE) HH:mm:ss", Locale.ENGLISH)

    // Convert the epoch time to an Instant, and format it in the desired time zone
    val formattedTime = Instant.ofEpochMilli(lastEndedAt)
        .atZone(ZoneId.systemDefault()) // Adjust to your local time zone
        .format(formatter)

    return "Ended at $formattedTime"
}

fun TimerItem.resetTimer(): TimerItem {
    return this.copy(remainingTime = presetTime)
}