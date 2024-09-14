package com.android.taskstimer.tasks_timer.presentation.components.timer

import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.filled.Pause
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.RestartAlt
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.android.taskstimer.core.domain.model.TimerItem
import com.android.taskstimer.core.domain.model.formatTime
import com.android.taskstimer.core.presentation.util.TestTags
import com.android.taskstimer.tasks_timer.presentation.components.MenuPopup

@Composable
fun Timer(
    timer: TimerItem,
    deleteTimer: () -> Unit = {},
    editTimer: () -> Unit = {}
) {
    var menuVisible by remember {
        mutableStateOf(false)
    }

    fun handleDeleteTimer() {
        menuVisible = false
        deleteTimer()
    }


    Column(
        modifier = Modifier
            .fillMaxWidth()
            .border(
                width = 1.dp,
                color = Color.White.copy(alpha = 0.3f),
                shape = RoundedCornerShape(0.dp)
            )
            .padding(10.dp)
    ) {
        TimerDetails(
            name = timer.name,
            time = timer.formatTime(),
            onMenuClick = { menuVisible = true }
        )
        if (menuVisible) {
            MenuPopup(dismiss = { menuVisible = false }) {
                TimerMenu(
                    deleteTimer = { handleDeleteTimer() },
                    editTimer = { editTimer() }
                )
            }
        }
    }
}


@Composable
private fun TimerDetails(
    name: String = "Timer name",
    time: String = "00:45",
    onMenuClick: () -> Unit = {},
) {
    Column(
        verticalArrangement = Arrangement.spacedBy(5.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween

        ) {
            Text(
                modifier = Modifier.testTag("${TestTags.TIMER} $name"),
                text = name,
                fontSize = 20.sp,
                color = Color.White
            )
            Icon(
                modifier = Modifier
                    .clickable {
                        onMenuClick()
                    }
                    .testTag("${TestTags.TIMER_MENU} $name"),
                imageVector = Icons.Filled.MoreVert,
                contentDescription = "Edit timer",
                tint = Color.White
            )
        }
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = time,
                fontSize = 45.sp,
                color = Color.White
            )
            Control()
        }
    }
}

@Composable
private fun Control(
    isActive: Boolean = false
) {
    Row(horizontalArrangement = Arrangement.spacedBy(5.dp)) {
        ControlButton(
            icon = Icons.Filled.RestartAlt,
            contentDescription = "Restart button",
            color = Color(0xFF3C6071),
            padding = PaddingValues(5.dp),
            onClick = {}
        )

        when (isActive) {
            true -> {
                ControlButton(
                    icon = Icons.Filled.Pause,
                    padding = PaddingValues(start = 14.dp, top = 5.dp, end = 15.dp, bottom = 5.dp),
                    contentDescription = "Pause button",
                    color = Color(0xFFFF5447),
                    onClick = {}
                )
            }

            false -> {
                ControlButton(
                    icon = Icons.Filled.PlayArrow,
                    padding = PaddingValues(start = 14.dp, top = 5.dp, end = 15.dp, bottom = 5.dp),
                    contentDescription = "Play button",
                    color = Color(0xFF629D61),
                    onClick = {}
                )

            }

        }
    }
}

@Composable
private fun ControlButton(
    color: Color,
    padding: PaddingValues,
    onClick: () -> Unit,
    icon: ImageVector,
    contentDescription: String

) {
    Button(
        modifier = Modifier.defaultMinSize(
            minWidth = 1.dp,
            minHeight = 1.dp
        ),
        colors = ButtonDefaults.buttonColors(
            containerColor = color
        ),
        contentPadding = padding,
        shape = RoundedCornerShape(10.dp),
        onClick = {}
    ) {
        Icon(
            modifier = Modifier.size(30.dp),
            imageVector = icon,
            contentDescription = contentDescription,
            tint = Color.White
        )
    }

}


private val previewTimerItem = TimerItem(
    name = "Check emails",
    remainingTime = "154"
)

@Preview
@Composable
private fun TimerPreview() {
    Timer(
        timer = previewTimerItem,
    )
}