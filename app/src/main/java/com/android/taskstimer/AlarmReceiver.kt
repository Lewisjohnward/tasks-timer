package com.android.taskstimer

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent

class AlarmReceiver: BroadcastReceiver() {

    override fun onReceive(context: Context?, intent: Intent?) {
        val message: String = intent?.getStringExtra("EXTRA_MESSAGE") ?: return
        println("Alarm triggered $message")

    }
}