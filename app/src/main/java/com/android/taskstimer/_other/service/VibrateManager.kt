package com.android.taskstimer._other.service

import android.content.Context
import android.media.Ringtone
import android.media.RingtoneManager
import android.net.Uri
import android.os.Build
import android.os.VibrationEffect
import android.os.Vibrator
import android.os.VibratorManager
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class VibrateManager @Inject constructor(
    @ApplicationContext private val context: Context
) {

    val DELAY = 0
    val VIBRATE = 1000
    val SLEEP = 1000
    val START = 0
    val vibratePattern = longArrayOf(DELAY.toLong(), VIBRATE.toLong(), SLEEP.toLong())

    val ONESHOT_DURATION: Long = 500L
    val ONESHOT_AMPLITUDE: Int = 255


    fun vibrate() {
        println(vibrator.hasVibrator())
//        vibrator.vibrate(
//            VibrationEffect.createOneShot(1000, 255)
//        )

//        vibrator.vibrate(
//            VibrationEffect.createPredefined(EFFECT_CLICK)
//        )

        //        vibrator.vibrate(
//            VibrationEffect.createWaveform(
//                longArrayOf(0, 255, 5000), 0
//            )
//        )
//        fun playRingtone(context: Context) {
//            // Get the default ringtone URI
//        // Get the default notification sound URI
//        val notificationUri: Uri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION)
//
//        // Get the ringtone object for the URI
//        val notificationRingtone: Ringtone = RingtoneManager.getRingtone(context, notificationUri)
//
//        // Play the notification sound
//        notificationRingtone.play()
//        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            vibrator.vibrate(VibrationEffect.createOneShot(ONESHOT_DURATION, ONESHOT_AMPLITUDE))

//            vibrator.vibrate(VibrationEffect.createWaveform(vibratePattern, START))

        } else {
            // backward compatibility for Android API < 26
            // noinspection deprecation
            @Suppress("DEPRECATION")
            vibrator.vibrate(vibratePattern, START)
        }
    }

    private val vibrator = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
        val vibratorManager: VibratorManager =
            context.getSystemService(Context.VIBRATOR_MANAGER_SERVICE) as VibratorManager
        vibratorManager.defaultVibrator

    } else {
        // backward compatibility for Android API < 31,
        // VibratorManager was only added on API level 31 release.
        // noinspection deprecation
        @Suppress("DEPRECATION")
        context.getSystemService(Context.VIBRATOR_SERVICE) as Vibrator
    }


}