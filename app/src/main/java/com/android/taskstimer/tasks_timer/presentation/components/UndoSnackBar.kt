package com.android.taskstimer.tasks_timer.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.Undo
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Icon
import androidx.compose.material3.SnackbarData
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarVisuals
import androidx.compose.material3.Text
import androidx.compose.material3.VerticalDivider
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.android.taskstimer.core.presentation.ui.theme.Green
import com.android.taskstimer.core.presentation.ui.theme.TasksTimerTheme


@Composable
fun UndoSnackBar(
    modifier: Modifier = Modifier,
    snackbarData: SnackbarData
) {
    Row(
        modifier = Modifier
            .height(50.dp)
            .background(Green)
            .padding(start = 10.dp, end = 10.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(80.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Row(
                modifier = Modifier
                    .weight(1f),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = snackbarData.visuals.message,
                    color = Color.White
                )
                VerticalDivider(
                    color = Color.White.copy(alpha = 0.3f)
                )
            }
            Row(
                modifier = Modifier
                    .padding(start = 10.dp, end = 10.dp)
                    .clickable {
                        snackbarData.performAction() // Perform the undo action
                    },
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.Undo, // Use undo n
                    tint = Color.White,
                    contentDescription = "Undo"
                )
                Text(
                    text = "UNDO",
                    color = Color.White
                )
            }
            Row(
                modifier = Modifier
                    .padding(start = 10.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                VerticalDivider(
                    color = Color.White.copy(alpha = 0.3f)
                )
                Icon(
                    modifier = Modifier.clickable { snackbarData.dismiss() },
                    tint = Color.White,
                    imageVector = Icons.Filled.Close,
                    contentDescription = null
                )
            }
        }
    }
}


@Preview
@Composable
fun PreviewUndoSnackBar() {
    TasksTimerTheme {
        UndoSnackBar(snackbarData = MockSnackbarData)
    }
}

object MockSnackbarData : SnackbarData {
    override val visuals: SnackbarVisuals
        get() = object : SnackbarVisuals {
            override val actionLabel: String
                get() = "Teach to sing deleted."
            override val duration: SnackbarDuration
                get() = SnackbarDuration.Short
            override val message: String
                get() = "Teach to sing deleted"
            override val withDismissAction: Boolean
                get() = true

        }

    override fun dismiss() {}
    override fun performAction() {}
}


