package com.android.taskstimer.presentation.screens.home

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.DrawerState
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.Surface
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.RectangleShape
import com.android.taskstimer.presentation.components.NavigationDrawer
import com.android.taskstimer.presentation.screens.Timers
import com.android.taskstimer.ui.theme.BackgroundDarkGray
import com.android.taskstimer.ui.theme.TasksTimerTheme
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import androidx.lifecycle.viewmodel.compose.viewModel
import com.android.taskstimer.presentation.AppViewModelProvider
import com.android.taskstimer.presentation.HomeViewModel
import com.android.taskstimer.presentation.TasksTimerEvent
import com.android.taskstimer.presentation.navigation.NavigationDestination


object HomeDestination : NavigationDestination {
    override val route = "home"
    override val title = "home"
}

@Composable
fun HomeScreen(
    coroutineScope: CoroutineScope = rememberCoroutineScope(),
    drawerState: DrawerState = rememberDrawerState(initialValue = DrawerValue.Closed),
    viewModel: HomeViewModel = viewModel(factory = AppViewModelProvider.Factory),
    onEvent: (TasksTimerEvent) -> Unit = viewModel::onEvent
) {

    val uiState by viewModel.uiState.collectAsState()
    fun openDrawer() {
        coroutineScope.launch { drawerState.open() }
    }
    TasksTimerTheme {
        Surface(
            modifier = Modifier
                .fillMaxSize(),
            color = BackgroundDarkGray
        )
        {
            Column {
                ModalNavigationDrawer(
                    drawerContent = {
                        ModalDrawerSheet(drawerShape = RectangleShape) {
                            NavigationDrawer()
                        }
                    },
                    drawerState = drawerState
                ) {
                    Timers(
                        uiState = uiState,
                        onEvent = onEvent,
                        openDrawer = { openDrawer() }
                    )
                }
            }
        }
    }
}

//@Preview(showBackground = true)
//@Composable
//fun TasksTimerAppPreview() {
//    val viewModel = AppViewModel()
//    val uiState by viewModel.uiState.collectAsState()
//    TasksTimerApp(uiState = uiState, onEvent = viewModel::onEvent)
//}