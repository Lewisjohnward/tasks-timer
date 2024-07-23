package com.android.taskstimer.tasks_timer.presentation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.ui.graphics.vector.ImageVector


data class ConfirmDialog(
    val message: String,
    val icon: ImageVector = Icons.Filled.Delete
)
