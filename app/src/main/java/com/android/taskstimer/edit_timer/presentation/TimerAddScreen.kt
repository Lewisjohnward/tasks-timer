package com.android.taskstimer.edit_timer.presentation

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.android.taskstimer.core.presentation.navigation.NavigationDestination
import com.android.taskstimer.core.presentation.ui.theme.BackgroundDarkGray
import com.android.taskstimer.edit_timer.presentation.components.NumPad
import com.android.taskstimer.tasks_timer.presentation.components.FloatingActionBtn
import com.android.taskstimer.tasks_timer.presentation.components.TimerTopBar

object TimerAddDestination : NavigationDestination {
    override val route = "timer_add"
    override val title = "Add timer"
    const val boardIdArg = "boardId"
    val routeWithArgs = "$route/{$boardIdArg}"
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TimerAddScreen(
    navigateBack: () -> Unit,
//    viewModel: TimerAddViewModel = hiltViewModel()
) {
//    val onEvent = viewModel::onEvent

    Scaffold(
        containerColor = BackgroundDarkGray,
        topBar = {
            TimerTopBar(
                title = "Add timer",
                iconOnclick = navigateBack
            )
        },
        floatingActionButton = {
            FloatingActionBtn(
                onClick = {
//                    onEvent(TimerAddEvent.AddTimer)
                    navigateBack()
                },
                icon = {
                    Image(
                        modifier = Modifier.size(30.dp),
                        imageVector = Icons.Filled.Add,
                        contentDescription = "Add timer",
                        colorFilter = ColorFilter.tint(Color.White)
                    )
                }
            )
        }
    ) { innerPadding ->
        Box(
            modifier = Modifier.padding(innerPadding),
        ) {
            Content()
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Content() {

    Column(
        modifier = Modifier
            .padding(top = 40.dp, start = 10.dp, end = 10.dp)
            .fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        NameInput()
        TimeInput()
        NumPad()
    }
}


@Composable
fun TimeInput() {
    val weight: Float = 1 / 3f

    Row(
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
        modifier = Modifier.Companion
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
                text = unit,
                color = Color.White,
                fontSize = 12.sp
            )
        }
    }
}

@Composable
fun NameInput() {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(10.dp)
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

@Preview(showBackground = true)
@Composable
fun TimerAddScreenPreview() {
    TimerAddScreen(navigateBack = { true })
}