package com.android.taskstimer.mediaPlayer

import android.media.MediaPlayer
import com.android.taskstimer.R
import com.android.taskstimer.TasksTimerApplication

object Mp {
    private var ctx: TasksTimerApplication
    ? = null

    fun setContext(tasksTimerApplication: TasksTimerApplication) {
        ctx = tasksTimerApplication

    }


    fun play(callback: () -> Unit){
        var mp: MediaPlayer? = null
        if (mp == null){
            mp = MediaPlayer.create(ctx, R.raw.alarm)
        }
        mp?.start()

        mp?.setOnCompletionListener { callback() }
    }
}
