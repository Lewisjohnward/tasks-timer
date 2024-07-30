package com.android.taskstimer.edit_timer.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Popup
import androidx.compose.ui.window.PopupProperties


data class Key(
    val key: String,
    val color: Color = Color.Black
)

val keys: List<Key> =
    listOf(
        Key(key = "1"),
        Key(key = "2"),
        Key(key = "3"),
        Key(key = "4"),
        Key(key = "5"),
        Key(key = "6"),
        Key(key = "7"),
        Key(key = "8"),
        Key(key = "9"),
        Key(
            key = "Start",
            color = Color(0xFF729656)
        ),
        Key(key = "0"),
        Key(key = "Del")
    )

@Composable
fun NumPad() {
    val padding = 5.dp
    Popup(
        alignment = Alignment.BottomEnd,
        properties = PopupProperties(
            excludeFromSystemGesture = true,
        ),
        // to dismiss on click outside
        onDismissRequest = {}
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .background(Color.White)
                .padding(5.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Center
            ) {
                Icon(
                    modifier = Modifier.size(25.dp),
                    imageVector = Icons.Filled.ArrowDropDown,
                    contentDescription = "Close numpad icon"
                )
            }
            Row(
                modifier = Modifier.padding(bottom = padding),
                horizontalArrangement = Arrangement.spacedBy(5.dp),
            ) {
                PadKey(modifier = Modifier.weight(0.5f), key = "-1")
                PadKey(modifier = Modifier.weight(0.5f), key = "+1")
            }
            LazyVerticalGrid(
                columns = GridCells.Fixed(count = 3),
                horizontalArrangement = Arrangement.spacedBy(padding),
                verticalArrangement = Arrangement.spacedBy(padding)
            ) {
                items(keys) { key ->
                    PadKey(
                        key = key.key,
                        color = key.color
                    )
                }

            }
        }
    }
}

@Composable
fun PadKey(modifier: Modifier = Modifier, key: String, color: Color = Color.Black) {
    Button(
        modifier = modifier,
        onClick = {},
        colors = ButtonDefaults.buttonColors(
            containerColor = color
        ),
        shape = RoundedCornerShape(5.dp)
    ) {
        Text(
            text = key,
            fontSize = 22.sp
        )
    }
}


@Preview(showBackground = true)
@Composable
fun NumPadPreview() {
    Column(modifier = Modifier.fillMaxSize()) {
        NumPad()
    }
}
