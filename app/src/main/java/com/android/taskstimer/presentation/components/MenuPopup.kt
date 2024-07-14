package com.android.taskstimer.presentation.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Popup
import androidx.compose.ui.window.PopupProperties
import com.android.taskstimer.ui.theme.BackgroundDarkGray

@Composable
fun MenuPopup(dismiss: () -> Unit) {
    Popup(
        alignment = Alignment.TopEnd,
        offset = IntOffset(1, 20),
        properties = PopupProperties(
            excludeFromSystemGesture = true,
        ),
        // to dismiss on click outside
        onDismissRequest = { dismiss() }
    ) {
        Card(
            modifier = Modifier,
//                .size(300.dp, 500.dp),
            shape = RoundedCornerShape(0.dp),
            colors = CardDefaults.cardColors(
                containerColor = BackgroundDarkGray
            ),
            elevation = CardDefaults.cardElevation(
                defaultElevation = 5.dp
            )
        ) {
            MenuPopupItem(icon = Icons.Filled.Edit, text = "Rename Board")
            MenuPopupItem(
                text = "Delete Board",
                contentsColor = Color(0xFFFF5447),
                icon = Icons.Filled.Delete
            )
        }
    }
}


@Composable
fun MenuPopupItem(
    text: String,
    contentsColor: Color = Color.White,
    icon: ImageVector

) {
    TextButton(
        onClick = { /*TODO*/ },
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            Icon(imageVector = icon, contentDescription = null, tint = contentsColor)
            Text(text = text, color = contentsColor)
        }
    }
}