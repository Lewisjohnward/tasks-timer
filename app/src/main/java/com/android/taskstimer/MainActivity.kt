package com.android.taskstimer

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.media.MediaPlayer
import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.core.app.ActivityCompat
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.android.taskstimer.presentation.TasksTimerApp
import com.android.taskstimer.ui.theme.TasksTimerTheme
import java.time.LocalDateTime

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val scheduler = AndroidAlarmScheduler(this)
        var alarmItem: AlarmItem? = null

        val CHANNEL_ID = "1"
            // Create the NotificationChannel, but only on API 26+ because
            // the NotificationChannel class is not in the Support Library.
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                val name = "CHANNEL_NAME"
                val descriptionText = "CHANNEL_DESCRIPTION"
                val importance = NotificationManager.IMPORTANCE_DEFAULT
                val channel = NotificationChannel(CHANNEL_ID, name, importance).apply {
                    description = descriptionText
                }
                // Register the channel with the system.
                val notificationManager: NotificationManager =
                    getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
                notificationManager.createNotificationChannel(channel)
            }

        // Create an explicit intent for an Activity in your app.
        val intent = Intent(this, MainActivity::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        }
        val pendingIntent: PendingIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_IMMUTABLE)

        var builder = NotificationCompat.Builder(this, CHANNEL_ID)
            .setSmallIcon(R.drawable.ic_launcher_foreground)
            .setContentTitle("My title")
            .setContentText("My content")
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
            .setContentIntent(pendingIntent)
            .setAutoCancel(true)


        fun showNotification(){
            with(NotificationManagerCompat.from(this)) {
                if (ActivityCompat.checkSelfPermission(
                        this@MainActivity,
                        android.Manifest.permission.POST_NOTIFICATIONS
                    ) != PackageManager.PERMISSION_GRANTED
                ) {
                    // TODO: Consider calling
                    // ActivityCompat#requestPermissions
                    // here to request the missing permissions, and then overriding
                    // public fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>,
                    //                                        grantResults: IntArray)
                    // to handle the case where the user grants the permission. See the documentation
                    // for ActivityCompat#requestPermissions for more details.

                    return@with
                }
                // notificationId is a unique int for each notification that you must define.
                notify(5 , builder.build())
            }
        }

        setContent {
            TasksTimerTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Button(
                            onClick = {
                                alarmItem = AlarmItem(
                                    time = LocalDateTime.now().plusSeconds("5".toLong()),
                                    message = "hello from message"
                                )
                                alarmItem?.let(scheduler::schedule)
                            },
                            shape = RoundedCornerShape(5.dp)
                        ) {
                            Text(
                                text = "Schedule alarm"
                            )
                        }
                        Button(
                            onClick = { controlSound(R.raw.vadaafareinculo) },
                            shape = RoundedCornerShape(5.dp)
                        ) {
                            Text(
                                text = "Play sound"
                            )
                        }
                        Button(
                            onClick = { showNotification() },
                            shape = RoundedCornerShape(5.dp)
                        ) {
                            Text(
                                text = "Show notification"
                            )
                        }
                    }
                }
            }
        }
    }

    private fun controlSound(id: Int) {
        println("hello")
        var mp: MediaPlayer? = null
        if (mp == null) {
            mp = MediaPlayer.create(this, id)
            Log.d("MainActivity", "ID ${mp!!.audioSessionId}")
        }

        mp?.start()

        Log.d("MainActivity", "Duration: ${mp!!.duration / 1000} seconds")

    }
}