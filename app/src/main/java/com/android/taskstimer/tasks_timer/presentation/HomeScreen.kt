package com.android.taskstimer.tasks_timer.presentation

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
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
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.android.taskstimer.R
import com.android.taskstimer._other.service.TasksTimerService
import com.android.taskstimer.core.presentation.navigation.NavigationDestination
import com.android.taskstimer.core.presentation.ui.theme.BackgroundDarkGray
import com.android.taskstimer.core.presentation.util.TestTags
import com.android.taskstimer.tasks_timer.presentation.components.BoardMenu
import com.android.taskstimer.tasks_timer.presentation.components.ConfirmDialog
import com.android.taskstimer.tasks_timer.presentation.components.FloatingActionBtn
import com.android.taskstimer.tasks_timer.presentation.components.MenuPopup
import com.android.taskstimer.tasks_timer.presentation.components.NavigationDrawer
import com.android.taskstimer.tasks_timer.presentation.components.Timer
import com.android.taskstimer.tasks_timer.presentation.components.TimerTopBar
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

    val uiState: HomeScreenUiState by viewModel.uiState.collectAsState()
    val onEvent: (HomeScreenEvent) -> Unit = viewModel::onEvent


    fun openDrawer() {
        viewModel.onEvent(HomeScreenEvent.EditBoards(false))
        coroutineScope.launch { drawerState.open() }
    }

    fun closeDrawer() {
        coroutineScope.launch { drawerState.close() }
    }

    // After adding a timer causes the service to load the timers
    LaunchedEffect(key1 = uiState.boards, key2 = true) {
        if (uiState.boards.isNotEmpty()) {
            tasksTimerService.selectBoard(uiState.boards[uiState.currentBoardIndex].id)
        }
    }

    // Triggered when choosing a board/deleting a board
    LaunchedEffect(key1 = viewModel.boardToLoad.value) {
        if (viewModel.boardToLoad.value == null) return@LaunchedEffect
        when (val boardToLoad: BoardToLoad = viewModel.boardToLoad.value!!) {
            is BoardToLoad.BoardId -> tasksTimerService.selectBoard(boardToLoad.int)
            is BoardToLoad.NullBoard -> tasksTimerService.selectBoard(null)
        }
        viewModel.boardToLoad.value = null
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
                        actionOnClick = { onEvent(HomeScreenEvent.DisplayMenu(true)) }
                    )
                },
                bottomBar = {
                    ToggleTimer(running = false)
                },
                floatingActionButton = {
                    FloatingActionBtn(
                        modifier = Modifier.testTag(TestTags.ADD_TIMER_FAB),
                        onClick = { navigateToAddTimer(tasksTimerService.state.value.boardItem.id) },
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
                        LazyColumn(
                            modifier = Modifier
//                .weight(0.8f)
                                .fillMaxWidth()
                                .padding(25.dp),
                            verticalArrangement = Arrangement.spacedBy(10.dp)
                        ) {
                            items(tasksTimerService.state.value.timers) { timer ->
                                Timer(timer = timer)
                            }
                        }
                }
            }
            if (uiState.displayBoardMenu) MenuPopup(
                dismiss = { onEvent(HomeScreenEvent.DisplayMenu(false)) },
            ) {
                BoardMenu(
                    rename = {},
                    delete = { onEvent(HomeScreenEvent.DeleteBoard(tasksTimerService.state.value.boardItem)) },
                )
            }
            if (uiState.displayConfirmDialog != null)
                ConfirmDialog(
                    confirm = { onEvent(HomeScreenEvent.DialogConfirm) },
                    cancel = { onEvent(HomeScreenEvent.DialogCancel) },
                )
        }
    )
}
