package com.android.taskstimer.tasks_timer.presentation.components

import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MoreVert
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
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.android.taskstimer.core.domain.model.TimerItem
import com.android.taskstimer.core.domain.model.formatTime
import com.android.taskstimer.core.presentation.util.TestTags

@Composable
fun Timer(timer: TimerItem) {
    var displayMenu by remember {
        mutableStateOf(false)
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
            onMenuClick = { displayMenu = true }
        )
        if (displayMenu) MenuPopup(dismiss = { displayMenu = false }, deleteBoard = {})
    }

}

@Composable
private fun TimerDetails(
    name: String = "Timer name",
    time: String = "00:45",
    onMenuClick: () -> Unit = {}
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
                .testTag(TestTags.TIMER_MENU),
            imageVector = Icons.Filled.MoreVert,
            contentDescription = "Edit timer",
            tint = Color.White
        )
    }
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(10.dp)
    ) {

        Text(
            text = time,
            fontSize = 45.sp,
            color = Color.White
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
    Timer(timer = previewTimerItem)
}