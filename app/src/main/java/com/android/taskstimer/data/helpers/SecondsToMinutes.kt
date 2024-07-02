package com.android.taskstimer.data.helpers

fun secondsToMinutes(seconds: Int): String {
    var formattedTime = ""



    if (seconds == 0) {
        return "00:00"
    } else if (seconds < 60) {
        if (seconds < 10) return ("00:0${seconds}")
        else return ("00:${seconds}")
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