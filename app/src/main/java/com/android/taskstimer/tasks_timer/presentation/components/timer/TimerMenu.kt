package com.android.taskstimer.tasks_timer.presentation.components.timer

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import com.android.taskstimer.tasks_timer.presentation.components.MenuPopupItem

@Composable
fun TimerMenu() {
    MenuPopupItem(
//        testTag = TestTags.MENU_RENAME_BOARD,
        icon = Icons.Filled.Edit, text = "Edit timer", onClick = {}
    )
    MenuPopupItem(
//        testTag = TestTags.MENU_DELETE_BOARD,
        text = "Delete timer",
        contentsColor = Color(0xFFFF5447),
        icon = Icons.Filled.Delete,
        onClick = {}
    )
}

