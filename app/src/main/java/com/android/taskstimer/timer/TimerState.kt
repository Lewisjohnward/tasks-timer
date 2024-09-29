package com.android.taskstimer.timer

import androidx.compose.runtime.rememberCoroutineScope
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class TimerState @Inject constructor(
    private val coroutineScope: CoroutineScope
) {

    val myFlow = MutableStateFlow("hello")

    companion object {

    }


}