package com.android.taskstimer.presentation.screens.timers

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.LocalContentColor
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.android.taskstimer.presentation.navigation.NavigationDestination
import com.android.taskstimer.ui.theme.BackgroundDarkGray

object TimerAddDestination : NavigationDestination {
    override val route = "home"
    override val title = ""
}

@Composable
fun TimerAddScreen(navigateBack: () -> Boolean) {

//    val localStyle = LocalTextStyle.current
//    val mergedStyle = localStyle.merge(TextStyle(color = LocalContentColor.current))
//    BasicTextField(
//        cursorBrush = SolidColor(MaterialTheme.colorScheme.primary),
//    )
    Surface(
        modifier = Modifier
            .fillMaxSize(),
        color = BackgroundDarkGray
    ) {
        Column(
            modifier = Modifier.padding(top = 40.dp, start = 10.dp, end = 10.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            NameInput()
            TimeInput()


        }
    }
}

@Composable
fun TimeInput() {
    val weight: Float = 1 / 3f

    Row(horizontalArrangement = Arrangement.spacedBy(5.dp)) {
        BasicTextField(
            modifier = Modifier
                .weight(weight)
                .border(width = 1.dp, color = Color.White, shape = RoundedCornerShape(5.dp))
                .padding(10.dp),
            textStyle = TextStyle(
                fontSize = 30.sp,
                color = Color.White,
                textAlign = TextAlign.Center
            ),
            value = "222", onValueChange = {})
        BasicTextField(
            modifier = Modifier
                .weight(weight)
                .border(width = 1.dp, color = Color.White, shape = RoundedCornerShape(5.dp))
                .padding(10.dp),
            textStyle = TextStyle(
                fontSize = 30.sp,
                color = Color.White,
                textAlign = TextAlign.Center
            ),
            value = "50", onValueChange = {}
        )
        Row(
            modifier = Modifier
                .weight(weight)
                .fillMaxHeight(0.1f)
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
                    text = "S",
                    color = Color.White,
                    fontSize = 12.sp
                )
            }
        }
    }
}

@Composable
fun NameInput() {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        BasicTextField(
            value = "My awesome new timer",
            textStyle = TextStyle(
                fontSize = 30.sp,
                color = Color.White
            ),
            singleLine = true,
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            enabled = true,
            maxLines = 1,
            cursorBrush = SolidColor(Color.White),
            onValueChange = {},
        )
        Spacer(
            modifier = Modifier
                .background(Color.White)
                .height(1.dp)
                .fillMaxWidth()
        )
    }
}
