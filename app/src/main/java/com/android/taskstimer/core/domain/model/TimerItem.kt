package com.android.taskstimer.core.domain.model

data class TimerItem(
    val id: Int = 0,
    val boardId: Int = 0,
    val name: String = "",
    val presetTime: String = "",
    val remainingTime: String = ""
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
    }}

fun TimerItem.resetTimer(): TimerItem {
    return this.copy(remainingTime = presetTime)
}