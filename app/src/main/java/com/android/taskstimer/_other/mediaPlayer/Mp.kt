package com.android.taskstimer._other.mediaPlayer

import android.content.Context
import android.media.AudioAttributes
import android.media.AudioFocusRequest
import android.media.AudioManager
import android.media.MediaPlayer
import com.android.taskstimer.R
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject
import javax.inject.Singleton


@Singleton
class MediaPlayerManager @Inject constructor(
    @ApplicationContext private val context: Context
) {
    private var mediaPlayer: MediaPlayer? = null
    private val audioManager = context.getSystemService(Context.AUDIO_SERVICE) as AudioManager

    private val audioFocusChangeListener = AudioManager.OnAudioFocusChangeListener { focusChange ->
        when (focusChange) {
            AudioManager.AUDIOFOCUS_GAIN -> {
                // Gained focus: start or resume playback
            }

            AudioManager.AUDIOFOCUS_LOSS -> {
                // Lost focus for an extended time: stop playback and release resources
            }

            AudioManager.AUDIOFOCUS_LOSS_TRANSIENT -> {
                // Lost focus for a short time: pause playback
            }

            AudioManager.AUDIOFOCUS_LOSS_TRANSIENT_CAN_DUCK -> {
                // Lost focus but app can continue playing at a lower volume (ducking)
            }
        }
    }

    private val audioAttributes = AudioAttributes.Builder()
        .setUsage(AudioAttributes.USAGE_ALARM)
        .setContentType(AudioAttributes.CONTENT_TYPE_MUSIC)
        .build()

    private val focusRequest =
        AudioFocusRequest.Builder(AudioManager.AUDIOFOCUS_GAIN_TRANSIENT_MAY_DUCK)
            .setAudioAttributes(audioAttributes)
            .setOnAudioFocusChangeListener(audioFocusChangeListener)
            .setAcceptsDelayedFocusGain(true)
            .build()


    fun play() {
        val result = audioManager.requestAudioFocus(focusRequest)
        if (
            result == AudioManager.AUDIOFOCUS_REQUEST_GRANTED &&
            mediaPlayer == null
        ) {
            mediaPlayer = MediaPlayer.create(context, R.raw.alarm)
            mediaPlayer?.start()
            mediaPlayer?.setOnCompletionListener {
                audioManager.abandonAudioFocusRequest(
                    focusRequest
                )
                it.release()
                mediaPlayer = null
            }
        }
    }
}
