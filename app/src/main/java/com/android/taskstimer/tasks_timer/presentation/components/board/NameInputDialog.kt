package com.android.taskstimer.tasks_timer.presentation.components.board

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.android.taskstimer.core.presentation.util.TestTags
import com.android.taskstimer.tasks_timer.presentation.HomeScreenEvent
import com.android.taskstimer.tasks_timer.presentation.components.dialog.Dialog

@Composable
fun NameInputDialog(
    myNewBoardName: String = "My awesome new board",
    close: () -> Unit = {},
    onEvent: (HomeScreenEvent) -> Unit = {},
) {
    // TODO: On process death this will be lost it needs
    // save state handle in the viewmodel
    var input by remember { mutableStateOf("") }

    Dialog {
        Column(
            verticalArrangement = Arrangement.spacedBy(20.dp)
        ) {
            Text(
                text = "New board name",
                color = Color.White,
                fontWeight = FontWeight.Bold
            )
            TextField(
                modifier = Modifier.testTag(TestTags.DIALOG_ADD_BOARD_INPUT_FIELD),
                value = input,
                placeholder = { Text(text = "My awesome new board") },
                onValueChange = { input = it },
                leadingIcon = {
                    Icon(
                        imageVector = Icons.Filled.Add,
                        contentDescription = "Add icon"
                    )
                },
                singleLine = true,
                colors = TextFieldDefaults.colors(
                    unfocusedContainerColor = Color.Transparent,
                    focusedContainerColor = Color.Transparent,
                    focusedTextColor = Color.White,
                    unfocusedTextColor = Color.White,
                    focusedLeadingIconColor = Color(0x99FFFFFF),
                    unfocusedLeadingIconColor = Color(0x44FFFFFF),
                    cursorColor = Color.White,
                    unfocusedPlaceholderColor = Color(0x22FFFFFF)

                ),
            )
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.End
            ) {
                TextButton(
                    modifier = Modifier.testTag(TestTags.DIALOG_ADD_BOARD_CONFIRM),
                    onClick = {
                        onEvent(HomeScreenEvent.CreateBoard(input))
                        close()
                    }) {
                    Text(
                        text = "confirm",
                        color = Color(0xFFFF9B88)
                    )
                }
                TextButton(
                    modifier = Modifier.testTag(TestTags.DIALOG_ADD_BOARD_CANCEL),
                    onClick = { close() }) {
                    Text(
                        text = "Cancel",
                        color = Color(0xFFFF9B88)

                    )
                }
            }
        }
    }
}