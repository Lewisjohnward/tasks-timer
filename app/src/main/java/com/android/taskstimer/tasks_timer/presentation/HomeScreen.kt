package com.android.taskstimer.tasks_timer.presentation

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.DrawerState
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.Scaffold
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.android.taskstimer.R
import com.android.taskstimer._other.service.TasksTimerService
import com.android.taskstimer.core.presentation.navigation.NavigationDestination
import com.android.taskstimer.core.presentation.ui.theme.BackgroundDarkGray
import com.android.taskstimer.tasks_timer.presentation.components.ConfirmDialog
import com.android.taskstimer.tasks_timer.presentation.components.FloatingActionBtn
import com.android.taskstimer.tasks_timer.presentation.components.MenuPopup
import com.android.taskstimer.tasks_timer.presentation.components.NavigationDrawer
import com.android.taskstimer.tasks_timer.presentation.components.TimerTopBar
import com.android.taskstimer.tasks_timer.presentation.components.Timers
import com.android.taskstimer.tasks_timer.presentation.components.ToggleTimer
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch


object HomeDestination : NavigationDestination {
    override val route = "home"
    override val title = "Tasks Timer Home"
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    coroutineScope: CoroutineScope = rememberCoroutineScope(),
    drawerState: DrawerState = rememberDrawerState(initialValue = DrawerValue.Closed),
    viewModel: HomeViewModel = hiltViewModel(),
    navigateToAddTimer: (Int) -> Unit,
    tasksTimerService: TasksTimerService,
) {

    val uiState: UiState by viewModel.uiState.collectAsState()
    val onEvent: (HomeScreenEvent) -> Unit = viewModel::onEvent

    var menuOpen: Boolean by remember { mutableStateOf(false) }

    fun openDrawer() {
        viewModel.onEvent(HomeScreenEvent.EditBoards(false))
        coroutineScope.launch { drawerState.open() }
    }

    fun closeDrawer() {
        coroutineScope.launch { drawerState.close() }
    }

    LaunchedEffect(key1 = true) {
        tasksTimerService.reloadBoard()
    }


    ModalNavigationDrawer(
        drawerContent = {
            NavigationDrawer(
                closeDrawer = { closeDrawer() },
                onEvent = onEvent,
                tasksTimerService = tasksTimerService,
                boards = uiState.boards,
                editBoards = uiState.editBoards
            )
        },
        drawerState = drawerState,
        content = {
            Scaffold(
                modifier = Modifier
                    .fillMaxSize(),
                containerColor = BackgroundDarkGray,
                topBar = {
                    TimerTopBar(
                        title = tasksTimerService.state.value.boardItem.name,
                        displayIcon = true,
                        iconOnclick = { openDrawer() },
                        scrollBehavior = null,
                        icon = Icons.Filled.Menu,
                        actionIcon = Icons.Filled.MoreVert,
                        actionOnClick = { menuOpen = true }
                    )
                },
                bottomBar = {
                    ToggleTimer(running = false)
                },
                floatingActionButton = {
                    FloatingActionBtn(onClick = { navigateToAddTimer(tasksTimerService.state.value.boardItem.id) },
                        icon = {
                            Image(
                                modifier = Modifier.size(30.dp),
                                painter = painterResource(R.drawable.timer_add),
                                contentDescription = "Add timer",
                                colorFilter = ColorFilter.tint(Color.White)
                            )

                        }
                    )

                }
            ) { innerPadding ->
                Box(modifier = Modifier.padding(innerPadding)) {
                    if (tasksTimerService.state.value.timers.isNotEmpty())
                        Timers(
                            timers = tasksTimerService.state.value.timers,
                            onEvent = onEvent,
                        )
                }
            }
            if (menuOpen) MenuPopup(
                dismiss = { menuOpen = false },
                deleteBoard = { onEvent(HomeScreenEvent.DeleteBoard(tasksTimerService.state.value.boardItem)) }
            )
            if (uiState.displayDialog != null)
                ConfirmDialog(
                    confirm = { onEvent(HomeScreenEvent.DialogConfirm) },
                    cancel = { onEvent(HomeScreenEvent.DialogCancel) },
                )
        }
    )
}
