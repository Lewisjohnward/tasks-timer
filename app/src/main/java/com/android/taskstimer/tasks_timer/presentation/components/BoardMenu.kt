package com.android.taskstimer.tasks_timer.presentation.components

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import com.android.taskstimer.core.presentation.util.TestTags

@Composable
fun BoardMenu(
    rename: () -> Unit = {},
    delete: () -> Unit = {}

){
    MenuPopupItem(
        testTag = TestTags.BOARD_MENU_RENAME_BOARD,
        icon = Icons.Filled.Edit, text = "Rename Board", onClick = {rename}
    )
    MenuPopupItem(
        testTag = TestTags.BOARD_MENU_DELETE_BOARD,
        text = "Delete Board",
        contentsColor = Color(0xFFFF5447),
        icon = Icons.Filled.Delete,
        onClick = {delete()}
    )
}