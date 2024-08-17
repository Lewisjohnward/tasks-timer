package com.android.taskstimer.tasks_timer.presentation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.ui.graphics.vector.ImageVector
import com.android.taskstimer.core.domain.model.BoardItem


data class ConfirmDialog(
    val message: String,
    val icon: ImageVector = Icons.Filled.Delete,
    val boardItem: BoardItem
)
