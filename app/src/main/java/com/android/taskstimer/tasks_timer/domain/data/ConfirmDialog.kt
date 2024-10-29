package com.android.taskstimer.tasks_timer.domain.data

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.ui.graphics.vector.ImageVector
import com.android.taskstimer.core.domain.model.BoardItem
import com.android.taskstimer.core.domain.model.TimerItem


const val message = "Are you sure you want to delete this"

sealed class DeleteDialog(
    val dialog: String,
    val icon: ImageVector = Icons.Filled.Delete
) {
    data class Board( val board: BoardItem): DeleteDialog(dialog = "$message board")
}
