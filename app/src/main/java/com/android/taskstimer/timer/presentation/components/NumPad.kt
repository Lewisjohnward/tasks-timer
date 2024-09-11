package com.android.taskstimer.timer.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp


private data class Key(
    val key: String,
    val color: Color = Color.Black
)

private val keys: List<Key> =
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
            key = "Add",
            color = Color(0xFF729656)
        ),
        Key(key = "0"),
        Key(key = "Del")
    )

@Composable
fun Numpad() {
    val padding = 5.dp
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color.White)
            .padding(5.dp)
    ) {
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

@Composable
private fun PadKey(modifier: Modifier = Modifier, key: String, color: Color = Color.Black) {
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


@OptIn(ExperimentalMaterial3Api::class)
@Preview(showBackground = true)
@Composable
private fun NumPadPreview() {
    Column(modifier = Modifier.fillMaxSize()) {
        Numpad()
    }
}


