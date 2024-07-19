package com.android.taskstimer.tasks_timer.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import com.android.taskstimer.tasks_timer.presentation.HomeScreenEvent


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun InputDialog(
    myNewBoardName: String = "My awesome new board name",
    cancel: () -> Unit = {},
    onEvent: (HomeScreenEvent) -> Unit = {}
) {
    AlertDialog(
        onDismissRequest = {},
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(5.dp))
                .background(Color(0xFFFFFFFF))
                .padding(20.dp)
        ) {
            Column {
                TextField(value = myNewBoardName, onValueChange = {})
                Row {
                    Button(onClick = { onEvent(HomeScreenEvent.CreateBoard(myNewBoardName)) }) {
                        Text(text = "confirm")
                    }
                    Button(onClick = { cancel() }) {
                        Text(text = "Cancel")
                    }
                }

            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun InputDialogPreview() {
    Column(
        Modifier.fillMaxSize()
    ) {
        InputDialog()
    }
}