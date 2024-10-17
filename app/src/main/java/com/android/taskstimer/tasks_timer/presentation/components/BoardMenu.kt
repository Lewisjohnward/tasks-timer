package com.android.taskstimer.tasks_timer.presentation.components

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Restore
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import com.android.taskstimer.core.presentation.util.TestTags

@Composable
fun BoardMenu(
    rename: () -> Unit = {},
    delete: () -> Unit = {},
    resetAll: () -> Unit = {}
) {
    MenuPopupItem(
        testTag = TestTags.BOARD_MENU_RENAME_BOARD,
        text = "Rename Board",
        icon = Icons.Filled.Edit,
        onClick = { rename() }
    )
    MenuPopupItem(
        testTag = TestTags.BOARD_MENU_RESET_ALL_TIMERS,
        text = "Reset all timers",
        icon = Icons.Filled.Restore,
        onClick = { resetAll() }
    )
    MenuPopupItem(
        testTag = TestTags.BOARD_MENU_DELETE_BOARD,
        text = "Delete Board",
        contentsColor = Color(0xFFFF5447),
        icon = Icons.Filled.Delete,
        onClick = { delete() }
    )
}