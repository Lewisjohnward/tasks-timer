package com.android.taskstimer.timer.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.android.taskstimer.core.presentation.util.TestTags
import com.android.taskstimer.timer.add_timer.presentation.TimerAddEvent

@Composable
fun NameInput(
    name: String,
    onEvent: (TimerAddEvent) -> Unit
) {
    Column(
        modifier = Modifier.background(Color.Transparent),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        BasicTextField(
            modifier = Modifier.testTag(TestTags.INPUT_FIELD),
            value = name,
            textStyle = TextStyle(
                fontSize = 30.sp,
                color = Color.White
            ),
            singleLine = true,
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Ascii),
            enabled = true,
            maxLines = 1,
            cursorBrush = SolidColor(Color.White),
            onValueChange = {onEvent(TimerAddEvent.UpdateName(it))},
        )
        Spacer(
            modifier = Modifier
                .background(Color.White)
                .height(1.dp)
                .fillMaxWidth()
        )
    }
}
