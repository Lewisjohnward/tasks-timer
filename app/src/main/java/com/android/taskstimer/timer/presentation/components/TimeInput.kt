package com.android.taskstimer.timer.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.android.taskstimer.core.presentation.ui.theme.BackgroundDarkGray
import com.android.taskstimer.core.presentation.ui.theme.Gainsboro


enum class Side {
    LEFT,
    MIDDLE,
    RIGHT
}

val ROUNDED_DP = 25.dp

@Composable
fun TimeInput() {
    val weight: Float = 1 / 3f


    val hours by remember { mutableIntStateOf(0) }
    val minutes by remember { mutableIntStateOf(0) }
    val seconds by remember { mutableIntStateOf(0) }

    val displayHours by remember {
        derivedStateOf {
            if (hours < 10) {
                "0$hours"
            } else "$hours"

        }
    }

    val displayMinutes by remember {
        derivedStateOf {
            if (minutes < 10) {
                "0$minutes"
            } else "$minutes"

        }
    }

    val displaySeconds by remember {
        derivedStateOf {
            if (seconds < 10) {
                "0$seconds"
            } else "$seconds"

        }
    }



    Row(
//        modifier = Modifier.height(800.dp),
        horizontalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        Input(value = displayHours, weight = weight, unit = "H", side = Side.LEFT)
        Input(value = displayMinutes, weight = weight, unit = "M", side = Side.MIDDLE)
        Input(value = displaySeconds, weight = weight, unit = "S", side = Side.RIGHT)
    }
}

@Composable
private fun RowScope.Input(
    value: String,
    weight: Float,
    unit: String,
    side: Side
) {

    val roundedCornerShape = when (side) {
        Side.LEFT -> RoundedCornerShape(ROUNDED_DP, 0.dp, 0.dp, ROUNDED_DP)
        Side.MIDDLE -> RoundedCornerShape(0.dp)
        Side.RIGHT -> RoundedCornerShape(0.dp, ROUNDED_DP, ROUNDED_DP, 0.dp)
    }

    // FocusRequester and Keyboard Controller
    val focusRequester = remember { FocusRequester() }
    val keyboardController = LocalSoftwareKeyboardController.current


    Row(
        modifier = Modifier
            .height(60.dp)
            .weight(weight)
            .clip(roundedCornerShape)
            .background(Gainsboro),
        horizontalArrangement = Arrangement.spacedBy(3.dp)
    ) {
        Column(
            modifier = Modifier
                .weight(1f)
                .fillMaxHeight(),
            verticalArrangement = Arrangement.Center
        ) {
            BasicTextField(
                modifier = Modifier.onFocusChanged { focusState ->
                    if (focusState.isFocused) {
                        keyboardController?.hide()
                    }

                },
                textStyle = TextStyle(
                    fontSize = 44.sp,
                    color = BackgroundDarkGray,
                    textAlign = TextAlign.End,
                    letterSpacing = 0.sp,
                ),
                keyboardActions = KeyboardActions(

                ),
                value = value, onValueChange = {}
            )
        }
        Column(
            modifier = Modifier
                .fillMaxHeight()
                .weight(0.4f)
                .padding(5.dp),
            verticalArrangement = Arrangement.Bottom,
        ) {
            Text(
                text = unit,
                color = BackgroundDarkGray,
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