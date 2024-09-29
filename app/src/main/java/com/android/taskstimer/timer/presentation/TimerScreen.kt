package com.android.taskstimer.timer.presentation

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material3.BottomSheetScaffold
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.SheetState
import androidx.compose.material3.SheetValue
import androidx.compose.material3.Text
import androidx.compose.material3.rememberBottomSheetScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.android.taskstimer.core.presentation.navigation.NavigationDestination
import com.android.taskstimer.core.presentation.ui.theme.BackgroundDarkGray
import com.android.taskstimer.core.presentation.util.TestTags
import com.android.taskstimer.tasks_timer.presentation.components.TimerTopBar
import com.android.taskstimer.timer.presentation.components.NameInput
import com.android.taskstimer.timer.presentation.components.Numpad
import com.android.taskstimer.timer.presentation.components.Side
import com.android.taskstimer.timer.presentation.components.TimeInput

object TimerDestination : NavigationDestination {
    override val route = "timer"
    override val title = "Add timer"
    const val boardIdArg = "boardId"
    const val timerIdArg = "timerId"
    val routeWithArgs = "$route/{$boardIdArg}/{$timerIdArg}"
}

sealed class NumpadEvent {
    data object Add : NumpadEvent()
    data object Delete : NumpadEvent()
    data object Increment : NumpadEvent()
    data object Decrement : NumpadEvent()
    data class Value(val value: Int) : NumpadEvent()
}

data class InputState(
    val focus: Boolean = false,
    val value: Int = 0,
    val unit: String,
    val side: Side,
)

fun InputState.displayValue(): String {
    return if (value < 10) {
        "0$value"
    } else "$value"
}

val timeInput = listOf<InputState>(
    InputState(unit = "H", side = Side.LEFT),
    InputState(focus = true, unit = "M", side = Side.MIDDLE),
    InputState(unit = "S", side = Side.RIGHT),
)

sealed interface UserAction {
    data class Click(val side: Side) : UserAction
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TimerScreen(
    navigateBack: () -> Unit,
    viewModel: TimerViewModel = hiltViewModel()
) {
    var timeInputState by remember { mutableStateOf(timeInput) }

    fun updateFocus(side: Side) {
        val updatedState = timeInputState.map { it ->
            if (side == it.side) {
                it.copy(focus = true)
            } else it.copy(focus = false)
        }
        timeInputState = updatedState
    }

    fun handleAction(action: UserAction) {
        when (action) {
            is UserAction.Click -> updateFocus(action.side)
        }
    }


    fun onClick(event: NumpadEvent) {
        when (event) {
            NumpadEvent.Decrement -> {
                val updatedState = timeInputState.map {
                    if (it.focus) {
                        if (it.value > 0) it.copy(value = it.value - 1) else it
                    } else it
                }
                timeInputState = updatedState
            }

            NumpadEvent.Increment -> {
                val updatedState = timeInputState.map {
                    if (it.focus) {
                        it.copy(value = it.value + 1)
                    } else it
                }
                timeInputState = updatedState
            }

            is NumpadEvent.Value -> {
                var focusNextInput = false
                // TODO: handle when pressing 0
                val updatedState = timeInputState.mapIndexed map@{ index, it ->

                    if (focusNextInput) {
                        focusNextInput = false
                        return@map it.copy(focus = true)
                    }

                    if (!it.focus) return@map it

                    if (it.value == 0) return@map it.copy(value = event.value)

                    if (it.value / 10 == 0) {
                        if (index < timeInputState.size - 1) focusNextInput = true
                        return@map it.copy(
                            value = ((it.value * 10) + event.value),
                            focus = !focusNextInput
                        )
                    }

                    it.copy(value = event.value)
                }
                timeInputState = updatedState

            }

            NumpadEvent.Add -> {
                println("Check is value and add!")
            }

            NumpadEvent.Delete -> {
                val updatedState = timeInputState.map {
                    if (it.focus) {
                        it.copy(value = 0)
                    } else it
                }
                timeInputState = updatedState

            }
        }
    }

    val uiState: TimerUiState by viewModel.uiState.collectAsState()
    val test: String by viewModel.test.collectAsState()
    val onEvent = viewModel::onEvent

    var sheetState = SheetState(
        skipPartiallyExpanded = false,
        density = LocalDensity.current,
        initialValue = SheetValue.Expanded
    )
    var scaffoldState = rememberBottomSheetScaffoldState(sheetState)
    val scope = rememberCoroutineScope()



    BottomSheetScaffold(
        sheetContent = { Numpad(onClick = ::onClick) },
        scaffoldState = scaffoldState,
        sheetPeekHeight = 25.dp,
        sheetContainerColor = Color.White,
        sheetDragHandle = {
            Icon(
                modifier = Modifier.size(25.dp),
                imageVector = Icons.Filled.ArrowDropDown,
                contentDescription = "Close numpad icon"
            )
        },
        topBar = {
            TimerTopBar(
                title = uiState.title,
                iconOnclick = navigateBack
            )
        },
        containerColor = BackgroundDarkGray,
//                sheetSwipeEnabled = true,
//                sheetShape =,
//                sheetContentColor =,
//                sheetTonalElevation =,
//                sheetShadowElevation =,
//                contentColor =,
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .padding(top = 80.dp, start = 10.dp, end = 10.dp)
                .fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(120.dp)
        ) {
            NameInput(
                name = uiState.timer.name,
                onEvent = onEvent
            )
            TimeInput(state = timeInputState, onClick = ::handleAction)
            Button(
                modifier = Modifier.testTag(TestTags.SAVE_BUTTON),
                onClick = {
                    onEvent(TimerEvent.AddTimer)
                    navigateBack()
                },
                enabled = uiState.isEntryValid
            ) {
                Text(text = "hello")
            }
        }
    }
}


@Preview(showBackground = true)
@Composable
fun TimerAddScreenPreview() {
    TimerScreen(navigateBack = { })
}