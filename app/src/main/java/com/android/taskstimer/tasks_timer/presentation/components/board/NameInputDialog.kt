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
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.android.taskstimer.R
import com.android.taskstimer.core.presentation.ui.theme.Coral
import com.android.taskstimer.core.presentation.util.TestTags
import com.android.taskstimer.tasks_timer.presentation.HomeScreenEvent

@Composable
fun NameInputDialog(
    onClose: () -> Unit = {},
    onEvent: (HomeScreenEvent) -> Unit = {},
    confirmEnabled: Boolean,
    newBoardName: String,
) {
    val confirmEnabled = newBoardName != ""

    Column(
//        modifier = Modifier.padding(start = 5.dp ,top = 20.dp, end = 5.dp, bottom = 20.dp),
        verticalArrangement = Arrangement.spacedBy(20.dp)
    ) {
        Text(
            text = stringResource(R.string.new_board_name),
            fontSize = 20.sp,
            color = Color.White,
            fontWeight = FontWeight.Bold
        )
        TextField(
            modifier = Modifier.testTag(TestTags.DIALOG_ADD_BOARD_INPUT_FIELD),
            value = newBoardName,
            placeholder = { Text(text = stringResource(R.string.add_new_board_placeholder)) },
            onValueChange = { onEvent(HomeScreenEvent.UpdateNewBoardName(it)) },
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
                modifier = Modifier.testTag(TestTags.DIALOG_ADD_BOARD_CANCEL),
                onClick = { onEvent(HomeScreenEvent.CancelCreateNewBoard) })
            {
                Text(
                    text = stringResource(R.string.cancel),
                    color = Color(0xFFFF9B88)
                )
            }
            TextButton(
                modifier = Modifier.testTag(TestTags.DIALOG_ADD_BOARD_CONFIRM),
                enabled = confirmEnabled,
                onClick = {
                    onEvent(HomeScreenEvent.NameNewBoard)
                    onClose()
                }) {
                Text(
                    text = stringResource(R.string.confirm),
                    color = if (confirmEnabled) Coral else Coral.copy(alpha = 0.3f)
                )
            }
        }
    }
}