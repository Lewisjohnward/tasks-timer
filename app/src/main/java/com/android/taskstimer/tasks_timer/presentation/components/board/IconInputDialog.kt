package com.android.taskstimer.tasks_timer.presentation.components.board

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.android.taskstimer.R
import com.android.taskstimer.core.presentation.ui.IconKey
import com.android.taskstimer.core.presentation.ui.theme.Coral
import com.android.taskstimer.core.presentation.util.TestTags
import com.android.taskstimer.core.presentation.util.thenIf
import com.android.taskstimer.tasks_timer.presentation.HomeScreenEvent

@Composable
fun IconInputDialog(
    myNewBoardName: String = "My awesome new board",
    onClose: () -> Unit = {},
    onEvent: (HomeScreenEvent) -> Unit = {},
    selectedIcon: IconKey,
) {

    // TODO: Add check to make sure icon is selected
    val confirmEnabled = true

    Column(
        verticalArrangement = Arrangement.spacedBy(20.dp)
    ) {
        Text(
            text = stringResource(R.string.choose_an_icon_for_the_new_board),
            fontSize = 20.sp,
            color = Color.White,
            fontWeight = FontWeight.Bold
        )
        LazyVerticalGrid(
            columns = GridCells.Fixed(4)
        ) {
            items(IconKey.entries.toTypedArray()) { iconKey ->
                IconButton(
                    modifier = Modifier
                        .padding(10.dp)
                        .thenIf(iconKey == selectedIcon) {
                            Modifier.border(
                                1.dp,
                                Color.White,
                                RoundedCornerShape(5.dp)
                            )
                        },
                    onClick = { onEvent(HomeScreenEvent.AssignIconNewBoard(iconKey)) },
                ) {
                    Icon(
                        modifier = Modifier
                            .size(40.dp),
                        imageVector = iconKey.icon,
                        contentDescription = "",
                        tint = Color.White
                    )
                }
            }
        }
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
                TextButton(
                    modifier = Modifier.testTag(TestTags.DIALOG_ADD_BOARD_CONFIRM),
                    enabled = confirmEnabled,
                    onClick = {
                        onEvent(HomeScreenEvent.AcceptNewBoard)
                    }) {
                    Text(
                        text = stringResource(R.string.confirm),
                        color = if (confirmEnabled) Coral else Coral.copy(alpha = 0.3f)
                    )
                }
            }
        }
    }
}