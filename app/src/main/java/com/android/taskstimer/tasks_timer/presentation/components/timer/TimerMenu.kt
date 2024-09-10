package com.android.taskstimer.tasks_timer.presentation.components.timer

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import com.android.taskstimer.core.domain.model.TimerItem
import com.android.taskstimer.core.presentation.util.TestTags
import com.android.taskstimer.tasks_timer.presentation.components.MenuPopupItem

@Composable
fun TimerMenu(
    deleteTimer: () -> Unit,
    timer: TimerItem
) {
    MenuPopupItem(
        testTag = TestTags.TIMER_MENU_RENAME_TIMER,
        icon = Icons.Filled.Edit, text = "Edit timer", onClick = {}
    )
    MenuPopupItem(
        testTag = TestTags.TIMER_MENU_DELETE_TIMER,
        text = "Delete timer",
        contentsColor = Color(0xFFFF5447),
        icon = Icons.Filled.Delete,
        onClick = {deleteTimer()}
    )
}

