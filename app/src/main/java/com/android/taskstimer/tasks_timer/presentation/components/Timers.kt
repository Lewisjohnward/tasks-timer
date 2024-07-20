package com.android.taskstimer.tasks_timer.presentation.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.android.taskstimer.core.domain.model.TimerItem
import com.android.taskstimer.tasks_timer.presentation.HomeScreenEvent
import com.android.taskstimer.tasks_timer.presentation.formatTime


@Composable
fun Timers(
    timers: List<TimerItem>,
    onEvent: (HomeScreenEvent) -> Unit,
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
    ) {
        TimerView(timers, onEvent)
    }
}


@Composable
private fun TimerView(
    timers: List<TimerItem>,
    onEvent: (HomeScreenEvent) -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize(),
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        LazyColumn(
            modifier = Modifier
                .weight(0.8f)
                .fillMaxWidth()
                .padding(25.dp),
            verticalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            itemsIndexed(timers) { index, timer ->
                TimerComposable(timer = timer, index = index)
            }
        }
        Button(running = false, onEvent = onEvent)
    }
}

@Composable
fun TimerComposable(timer: TimerItem, index: Int) {
    Column(
        modifier = Modifier
            .height(100.dp)
    ) {
        Text(
            text = timer.name,
            fontSize = 20.sp,
            color = Color.White
        )
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            Text(
                text = index.toString(),
                fontSize = 15.sp,
                fontWeight = FontWeight(100),
                color = Color.White
            )
            Text(
                text = timer.formatTime(),
                fontSize = 45.sp,
                color = Color.White
            )
        }
    }
}

@Composable
private fun Button(onEvent: (HomeScreenEvent) -> Unit, running: Boolean) {
    val icon: ImageVector = if (running) Icons.Filled.Menu else Icons.Filled.PlayArrow

    Column(
        modifier = Modifier
            .fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Button(
            onClick = { onEvent(HomeScreenEvent.ToggleTimer) },
            shape = RoundedCornerShape(5.dp)
        ) {
            Icon(
                imageVector = icon,
                contentDescription = null
            )

        }
    }
}