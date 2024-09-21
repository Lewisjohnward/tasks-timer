package com.android.taskstimer.tasks_timer.presentation.components.board

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.android.taskstimer.R
import com.android.taskstimer.core.presentation.ui.IconKey
import com.android.taskstimer.tasks_timer.presentation.HomeScreenEvent
import com.android.taskstimer.tasks_timer.presentation.components.dialog.Dialog

@Composable
fun IconInputDialog(
    myNewBoardName: String = "My awesome new board",
    close: () -> Unit = {},
    onEvent: (HomeScreenEvent) -> Unit = {},
) {

    Dialog {
        Column(
            verticalArrangement = Arrangement.spacedBy(20.dp)
        ) {
            Text(
                text = stringResource(R.string.choose_an_icon_for_the_new_board),
                color = Color.White,
                fontWeight = FontWeight.Bold
            )
            LazyVerticalGrid(
                columns = GridCells.Fixed(4)
            ) {
                items(IconKey.entries.toTypedArray()) { iconKey ->
                    IconButton(
                        modifier = Modifier.padding(10.dp),
                        onClick = {},
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
        }
    }
}