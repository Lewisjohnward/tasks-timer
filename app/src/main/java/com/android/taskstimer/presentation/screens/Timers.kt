package com.android.taskstimer.presentation.screens

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.outlined.Add
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.android.taskstimer.data.Timer
import com.android.taskstimer.presentation.TasksTimer
import com.android.taskstimer.presentation.TasksTimerEvent
import com.android.taskstimer.presentation.formatTime


@Composable
fun Timers(
    uiState: TasksTimer,
    onEvent: (TasksTimerEvent) -> Unit,
    openDrawer: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
    ) {
        Header(openDrawer)
        TimerView(uiState, onEvent)
    }
}

@Composable
private fun Header(openDrawer: () -> Unit) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(20.dp)
    ) {
        Button(
            onClick = { openDrawer() },
            colors = ButtonDefaults.buttonColors(
                containerColor = Color.Transparent,
            )
        ) {
            Icon(imageVector = Icons.Filled.Menu, contentDescription = "Menu icon")
        }
        Text(
            text = "Super timer name",
            fontSize = 25.sp,
            color = Color.White
        )
    }
}


@Composable
private fun TimerView(
    uiState: TasksTimer,
    onEvent: (TasksTimerEvent) -> Unit
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
            itemsIndexed(uiState.timers) { index, timer ->
                TimerComposable(timer = timer, index = index)
            }
            item {
                AddTimerComposable()
            }
        }
        Button(running = uiState.running, onEvent = onEvent)
    }
}

@Composable
fun AddTimerComposable(
    timer: Timer = Timer(name = "test", presetTime = "44"),
    index: Int = 4
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .height(100.dp)
            .border(width = 1.dp, color = Color.White, shape = RoundedCornerShape(10.dp)),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        IconButton(onClick = { /*TODO*/ }) {
            Icon(
                modifier = Modifier.size(100.dp),
                imageVector = Icons.Outlined.Add,
                contentDescription = "Add timer",
                tint = Color.White,
            )
        }
    }
}

@Composable
fun TimerComposable(timer: Timer, index: Int) {
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
private fun Button(onEvent: (TasksTimerEvent) -> Unit, running: Boolean) {
    val icon: ImageVector = if (running) Icons.Filled.Menu else Icons.Filled.PlayArrow

    Column(
        modifier = Modifier
            .fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Button(
            onClick = { onEvent(TasksTimerEvent.ToggleTimer) },
            shape = RoundedCornerShape(5.dp)
        ) {
            Icon(
                imageVector = icon,
                contentDescription = null
            )

        }
    }
}


@Preview()
@Composable
fun TimersPreview() {
    Timers(uiState = TasksTimer(
        running = false,
        finished = false,
        coroutineId = null,
        currentTimerIndex = 0,
        timers = listOf()
    ), onEvent = { }, openDrawer = { -> })
}