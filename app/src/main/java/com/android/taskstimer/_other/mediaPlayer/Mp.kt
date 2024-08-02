package com.android.taskstimer._other.mediaPlayer

import android.content.Context
import android.media.MediaPlayer
import com.android.taskstimer.R
import com.android.taskstimer.TasksTimerApplication

object Mp {
    private var ctx: TasksTimerApplication
    ? = null

    fun setContext(tasksTimerApplication: TasksTimerApplication) {
        ctx = tasksTimerApplication
    }


    fun play(context: Context){
        var mp: MediaPlayer? = null
        if (mp == null){
            mp = MediaPlayer.create(context, R.raw.alarm)
        }
        mp?.start()

//        mp?.setOnCompletionListener { callback() }
    }
}
