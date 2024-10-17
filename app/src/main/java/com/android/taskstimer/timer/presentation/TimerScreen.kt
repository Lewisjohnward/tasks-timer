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
import androidx.compose.runtime.rememberCoroutineScope
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
import com.android.taskstimer.timer.InputState
import com.android.taskstimer.timer.presentation.components.NameInput
import com.android.taskstimer.timer.presentation.components.Numpad
import com.android.taskstimer.timer.presentation.components.TimeInput

object TimerDestination : NavigationDestination {
    override val route = "timer"
    override val title = "Add timer"
    const val boardIdArg = "boardId"
    const val timerIdArg = "timerId"
    val routeWithArgs = "$route/{$boardIdArg}/{$timerIdArg}"
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TimerScreen(
    navigateBack: () -> Unit,
    viewModel: TimerViewModel = hiltViewModel()
) {


    val uiState: TimerUiState by viewModel.uiState.collectAsState()
    val timerState: List<InputState> by viewModel.timerState.collectAsState()
    val onEvent = viewModel::onEvent

    var sheetState = SheetState(
        skipPartiallyExpanded = false,
        density = LocalDensity.current,
        initialValue = SheetValue.Expanded
    )
    var scaffoldState = rememberBottomSheetScaffoldState(sheetState)
    val scope = rememberCoroutineScope()


    BottomSheetScaffold(
        sheetContent = { Numpad(onClick = onEvent) },
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
                iconOnclick = navigateBack,
                iconEnabled = true
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
            TimeInput(state = timerState, onEvent = onEvent)
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