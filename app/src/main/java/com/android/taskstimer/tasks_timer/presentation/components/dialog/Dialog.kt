package com.android.taskstimer.tasks_timer.presentation.components.dialog

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.BasicAlertDialog
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.android.taskstimer.core.presentation.ui.theme.BackgroundDarkGray
import com.android.taskstimer.core.presentation.ui.theme.TasksTimerTheme


@Composable
fun Dialog(
    content: @Composable () -> Unit = {}
) {
    BasicAlertDialog(onDismissRequest = {}) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(50.dp))
                .background(BackgroundDarkGray)
                .padding(start = 25.dp ,top = 40.dp, end = 25.dp, bottom = 40.dp)
        ) {
            content()

        }
    }
}

@Preview(showBackground = true)
@Composable
fun InputDialogPreview() {
    TasksTimerTheme {
        Column(
            Modifier.fillMaxSize()
        ) {
            Dialog()
        }
    }
}