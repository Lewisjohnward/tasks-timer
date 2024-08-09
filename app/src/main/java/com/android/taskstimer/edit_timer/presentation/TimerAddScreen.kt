package com.android.taskstimer.edit_timer.presentation

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material3.BottomSheetScaffold
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.SheetState
import androidx.compose.material3.SheetValue
import androidx.compose.material3.rememberBottomSheetScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.android.taskstimer.core.presentation.navigation.NavigationDestination
import com.android.taskstimer.core.presentation.ui.theme.BackgroundDarkGray
import com.android.taskstimer.edit_timer.presentation.components.NameInput
import com.android.taskstimer.edit_timer.presentation.components.Numpad
import com.android.taskstimer.edit_timer.presentation.components.TimeInput
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
    viewModel: TimerAddViewModel = hiltViewModel()
) {
//    val onEvent = viewModel::onEvent

    var sheetState = SheetState(skipPartiallyExpanded = false, initialValue = SheetValue.Expanded)
    var scaffoldState = rememberBottomSheetScaffoldState(sheetState)
    val scope = rememberCoroutineScope()


    BottomSheetScaffold(
        sheetContent = { Numpad() },
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
                title = "Add timer",
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
        Box(modifier = Modifier.padding(innerPadding)) {
            Column(
                modifier = Modifier
                    .padding(top = 80.dp, start = 10.dp, end = 10.dp)
                    .fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(120.dp)
            ) {
                NameInput()
                TimeInput()
            }
        }
    }
}


@Preview(showBackground = true)
@Composable
fun TimerAddScreenPreview() {
    TimerAddScreen(navigateBack = { true })
}