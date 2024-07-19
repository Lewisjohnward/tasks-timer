package com.android.taskstimer._other.service

import android.app.Service
import android.content.Intent
import android.os.IBinder

class TasksTimerService : Service() {


    override fun onBind(intent: Intent?): IBinder? {
        return null
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        println("onStartCommand")
        println(intent)
//        when (intent.action) {
//            is "start" -> start()
//            is "stop" -> stopSelf()
//        }
        return super.onStartCommand(intent, flags, startId)
    }


}