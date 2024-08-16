package com.android.taskstimer.tasks_timer.presentation.components

import android.content.Intent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.android.taskstimer._other.service.TasksTimerService

@Composable
fun ToggleTimer(running: Boolean = true) {
    val context = LocalContext.current
    val icon: ImageVector = if (running) Icons.Filled.Menu else Icons.Filled.PlayArrow

    Column(
        modifier = Modifier
            .fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Button(
            onClick = {
                val intent = Intent(context, TasksTimerService::class.java)
                intent.putExtra(
                    TasksTimerService.SERVICE_ACTION,
                    TasksTimerService.START_TASKS_TIMER
                )
                context.startService(intent)
            },
            shape = RoundedCornerShape(5.dp)
        ) {
            Icon(
                imageVector = icon,
                contentDescription = null
            )

        }
    }
}
