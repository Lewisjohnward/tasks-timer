package com.android.taskstimer.tasks_timer.presentation

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.DrawerState
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.Scaffold
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.android.taskstimer.R
import com.android.taskstimer._other.service.RUNSTATE
import com.android.taskstimer._other.service.TasksTimerService
import com.android.taskstimer.core.presentation.dragDropList.DragDropList
import com.android.taskstimer.core.presentation.dragDropList.DragDropListState
import com.android.taskstimer.core.presentation.dragDropList.rememberDragDropListState
import com.android.taskstimer.core.presentation.navigation.NavigationDestination
import com.android.taskstimer.core.presentation.ui.theme.BackgroundDarkGray
import com.android.taskstimer.core.presentation.util.TestTags
import com.android.taskstimer.tasks_timer.presentation.components.BoardMenu
import com.android.taskstimer.tasks_timer.presentation.components.ConfirmDialog
import com.android.taskstimer.tasks_timer.presentation.components.FloatingActionBtn
import com.android.taskstimer.tasks_timer.presentation.components.MenuPopup
import com.android.taskstimer.tasks_timer.presentation.components.NavigationDrawer
import com.android.taskstimer.tasks_timer.presentation.components.TimerTopBar
import com.android.taskstimer.tasks_timer.presentation.components.timer.Timer
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch


const val ADD_TIMER = 0

object HomeDestination : NavigationDestination {
    override val route = "home"
    override val title = "Tasks Timer Home"
}

@Composable
fun HomeScreen(
    navigateToTimer: (Int, Int) -> Unit,
    navigateToSettings: () -> Unit,
    tasksTimerService: TasksTimerService,
) {
    HomeScreenContent(
        navigateToTimer = navigateToTimer,
        navigateToSettings = navigateToSettings,
        tasksTimerService = tasksTimerService
    )
}

@Composable
private fun HomeScreenContent(
    coroutineScope: CoroutineScope = rememberCoroutineScope(),
    drawerState: DrawerState = rememberDrawerState(initialValue = DrawerValue.Closed),
    viewModel: HomeViewModel = hiltViewModel(),
    navigateToTimer: (Int, Int) -> Unit,
    tasksTimerService: TasksTimerService,
    navigateToSettings: () -> Unit,
) {

    val uiState: HomeScreenUiState by viewModel.uiState.collectAsState()
    // TODO: PUT THIS WITH VIEWMODEL STATE
    val tasksTimerManagerState by viewModel.tasksTimerManagerState.collectAsState()

    val onEvent: (HomeScreenEvent) -> Unit = viewModel::onEvent

    val dragDropListState: DragDropListState = rememberDragDropListState(
        onMove = { fromIndex, toIndex -> println("Drag drop from $fromIndex to $toIndex") }
    )

    fun openDrawer() {
        viewModel.onEvent(HomeScreenEvent.EditBoards(false))
        coroutineScope.launch { drawerState.open() }
    }


    fun closeDrawer() {
        coroutineScope.launch { drawerState.close() }
    }

//     Ensure that board up to date
    LaunchedEffect(true) {
        // TODO: ON FIRST LAUNCH NO BOARD PRESENT SO BLANK SCREEN
        viewModel.loadBoard()
    }

    ModalNavigationDrawer(
        drawerContent = {
            NavigationDrawer(
                closeDrawer = { closeDrawer() },
                onEvent = onEvent,
                navigateToSettings = { navigateToSettings() },
                tasksTimerService = tasksTimerService,
                boards = uiState.boards,
                editBoards = uiState.editBoards,
                createBoard = uiState.createBoard
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
                        title = tasksTimerManagerState.board,
                        displayIcon = true,
                        iconOnclick = { openDrawer() },
                        scrollBehavior = null,
                        icon = Icons.Filled.Menu,
                        actionIcon = Icons.Filled.MoreVert,
                        actionOnClick = { onEvent(HomeScreenEvent.DisplayMenu(true)) },
                        actionEnabled = uiState.boardMenuEnabled
                    )
                },
                floatingActionButton = {
                    FloatingActionBtn(
                        modifier = Modifier.testTag(TestTags.ADD_TIMER_FAB),
                        onClick = {
                            navigateToTimer(
                                tasksTimerService.state.value.boardItem.id,
                                ADD_TIMER
                            )
                        },
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
                    if (tasksTimerManagerState.timers.isNotEmpty())
                        DragDropList(
                            dragDropListState = dragDropListState,
                            contentPadding = PaddingValues(top = 5.dp),
                            verticalArrangement = Arrangement.spacedBy(10.dp)
                        ) {

                            itemsIndexed(tasksTimerManagerState.timers) { index, timer ->
                                val tasksTimerActive = tasksTimerManagerState.active
                                val timerActive = if (
                                    tasksTimerManagerState.currentTimer == index &&
                                    tasksTimerManagerState.active == RUNSTATE.RUNNING
                                ) RUNSTATE.RUNNING else RUNSTATE.STOPPED
                                Timer(
                                    modifier = Modifier.composed {
                                        val offsetOrNull =
                                            dragDropListState.elementDisplacement.takeIf {
                                                index == dragDropListState.currentIndexOfDraggedItem
                                            }
                                        Modifier.graphicsLayer {
                                            translationY = offsetOrNull ?: 0f
                                        }
                                    },
                                    tasksTimerActive = tasksTimerActive,
                                    timerActive = timerActive,
                                    index = index,
                                    timer = timer,
                                    deleteTimer = {
                                        onEvent(HomeScreenEvent.DeleteTimer(timer))
                                    },
                                    editTimer = {
                                        navigateToTimer(
                                            timer.boardId,
                                            timer.id
                                        )
                                    },
                                    startTimer = {viewModel.startTimer(index)},
                                    pauseTimer = {viewModel.pauseTimer()},
                                    resetTimer = {viewModel.resetTimer(index)}
                                )
                            }
                            item {
                                Spacer(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .height(75.dp)
                                )
                            }
                        }
                }
            }
            if (uiState.displayBoardMenu) MenuPopup(
                dismiss = { onEvent(HomeScreenEvent.DisplayMenu(false)) },
            ) {
                BoardMenu(
                    rename = {},
                    delete = { onEvent(HomeScreenEvent.DeleteBoard) },
                )
            }
            if (uiState.displayConfirmDialog != null)
                ConfirmDialog(
                    dialog = uiState.displayConfirmDialog!!,
                    confirm = { onEvent(HomeScreenEvent.DialogConfirm) },
                    cancel = { onEvent(HomeScreenEvent.DialogCancel) },
                )
        }
    )
}
