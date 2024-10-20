package com.android.taskstimer.timer.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.android.taskstimer.core.presentation.ui.theme.BackgroundDarkGray
import com.android.taskstimer.core.presentation.ui.theme.Gainsboro
import com.android.taskstimer.core.presentation.util.thenIf
import com.android.taskstimer.timer.InputState
import com.android.taskstimer.timer.displayValue
import com.android.taskstimer.timer.presentation.TimerEvent


enum class Side {
    LEFT,
    MIDDLE,
    RIGHT
}

val ROUNDED_DP = 25.dp
const val FOCUS_BG = 0XFF999999

@Composable
fun TimeInput(
    state: List<InputState>,
    onEvent: (TimerEvent) -> Unit,
    onFocus: () -> Unit
) {
    val weight: Float = 1 / 3f

    Row(
        horizontalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        state.forEach { input ->
            Input(
                modifier = Modifier.clickable {
                    onFocus()
                    onEvent(TimerEvent.ChangeFocus(input.side))
                },
                focus = input.inFocus,
                value = input.displayValue(),
                weight = 0.33f,
                unit = input.unit,
                side = input.side
            )
        }
    }
}

@Composable
fun RowScope.Input(
    modifier: Modifier = Modifier,
    focus: Boolean,
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

    Row(
        modifier = modifier
            .height(60.dp)
            .weight(weight)
            .clip(roundedCornerShape)
            .background(Gainsboro)
            .thenIf(focus) {
                Modifier
                    .border(1.dp, Color.White, roundedCornerShape)
                    .background(Color(FOCUS_BG))
            },
        horizontalArrangement = Arrangement.spacedBy(3.dp)
    ) {
        Column(
            modifier = Modifier
                .weight(1f)
                .fillMaxHeight(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.End

        ) {
            Text(
                text = value,
                fontSize = 48.sp,
                color = BackgroundDarkGray,
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

//@Preview(
//    backgroundColor = 0xFF263238
//)
//@Composable
//fun TimeInputPreview() {
//    TimeInput(, {})
//}


