package com.android.taskstimer.tasks_timer.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.BasicAlertDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.unit.dp
import com.android.taskstimer.core.presentation.util.TestTags
import com.android.taskstimer.tasks_timer.domain.data.DeleteDialog

@Composable
fun ConfirmDialog(
    dialog: DeleteDialog,
    confirm: () -> Unit,
    cancel: () -> Unit
) {
    BasicAlertDialog(
        onDismissRequest = { cancel() }
    ) {
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
                Text(text = dialog.dialog)
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.End
                ) {
                    TextButton(
                        modifier = Modifier.testTag(TestTags.CONFIRM_DELETE),
                        onClick = { confirm() }
                    ) {
                        Text(text = "Confirm")
                    }
                    TextButton(onClick = { cancel() }) {
                        Text(text = "Cancel")
                    }
                }
            }
        }
    }
}
