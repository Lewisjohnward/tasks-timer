package com.android.taskstimer.core.utils

object Formatter {
    fun formatTime(seconds: Int): String{
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
}