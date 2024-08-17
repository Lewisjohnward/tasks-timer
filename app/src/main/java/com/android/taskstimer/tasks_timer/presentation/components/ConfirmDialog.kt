package com.android.taskstimer.tasks_timer.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.android.taskstimer.core.domain.model.BoardItem
import com.android.taskstimer.tasks_timer.presentation.ConfirmDialog

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ConfirmDialog(
    dialog: ConfirmDialog = ConfirmDialog(
        message = "Are you sure you want to delete this board?",
        icon = Icons.Filled.Delete,
        boardItem = BoardItem()
    ),
    confirm: () -> Unit,
    cancel: () -> Unit
)
 {
    AlertDialog(onDismissRequest = {}) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(5.dp))
                .background(Color(0xFFFFFFFF))
                .padding(20.dp)
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(20.dp)

            ) {
                Icon(
                    imageVector = dialog.icon,
                    contentDescription = "Delete icon",
                    tint = Color(0xFF555555)
                )
                Text(text = dialog.message)
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.End
                ) {
                    TextButton(onClick = {confirm()}) {
                        Text(text = "Confirm")
                    }
                    TextButton(onClick = {cancel()}) {
                        Text(text = "Cancel")
                    }
                }
            }
        }
    }
}
