package com.android.taskstimer.tasks_timer.presentation.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.FloatingActionButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.android.taskstimer.R

@Composable
fun FloatingActionBtn(
    onClick: () -> Unit = {},
    icon: @Composable () -> Unit
) {
    FloatingActionButton(
        containerColor = Color(0xFF629D61),
        shape = RoundedCornerShape(50.dp),
        onClick = { onClick() }
    ) {
        icon()
    }
}
