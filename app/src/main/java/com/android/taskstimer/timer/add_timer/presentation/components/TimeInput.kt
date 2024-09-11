package com.android.taskstimer.timer.add_timer.presentation.components

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun TimeInput() {
    val weight: Float = 1 / 3f

    Row(
//        modifier = Modifier.height(800.dp),
        horizontalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        Input(weight = weight, unit = "H")
        Input(weight = weight, unit = "M")
        Input(weight = weight, unit = "S")
    }
}

@Composable
private fun RowScope.Input(weight: Float, unit: String) {
    Row(
        modifier = Modifier
            .height(75.dp)
            .weight(weight)
//            .fillMaxHeight(0.1f)
            .border(width = 1.dp, color = Color.White, shape = RoundedCornerShape(5.dp))
            .padding(5.dp),
        horizontalArrangement = Arrangement.spacedBy(3.dp)
    ) {
        Column(
            modifier = Modifier
                .weight(1f)
                .fillMaxHeight(),
            verticalArrangement = Arrangement.Center
        ) {
            BasicTextField(
                textStyle = TextStyle(
                    fontSize = 44.sp,
                    color = Color.White,
                    textAlign = TextAlign.End
                ),
                value = "54", onValueChange = {}
            )
        }
        Column(
            modifier = Modifier
                .fillMaxHeight()
                .weight(0.4f),
            verticalArrangement = Arrangement.Bottom,
        ) {
            Text(
                text = unit,
                color = Color.White,
                fontSize = 12.sp
            )
        }
    }
}

@Preview(
    backgroundColor = 0xFF263238
)
@Composable
fun TimeInputPreview() {
        TimeInput()
}