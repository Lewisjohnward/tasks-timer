package com.android.taskstimer.presentation.screens.home

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.DrawerState
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.Scaffold
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.android.taskstimer.presentation.components.NavigationDrawer
import com.android.taskstimer.presentation.components.Timers
import com.android.taskstimer.ui.theme.BackgroundDarkGray
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import androidx.lifecycle.viewmodel.compose.viewModel
import com.android.taskstimer.R
import com.android.taskstimer.data.timer.Timer
import com.android.taskstimer.presentation.AppViewModelProvider
import com.android.taskstimer.presentation.components.TimerTopBar
import com.android.taskstimer.presentation.navigation.NavigationDestination
import kotlinx.coroutines.Job


object HomeDestination : NavigationDestination {
    override val route = "home"
    override val title = "Tasks Timer Home"
}


data class FakeTasksTimer(
    val running: Boolean = false,
    val finished: Boolean = false,
    val coroutineId: Job? = null,
    val currentTimerIndex: Int = 0,
    val timers: List<Timer> = listOf()
)


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    coroutineScope: CoroutineScope = rememberCoroutineScope(),
    drawerState: DrawerState = rememberDrawerState(initialValue = DrawerValue.Closed),
    viewModel: HomeViewModel = viewModel(factory = AppViewModelProvider.Factory),
    onEvent: (HomeScreenEvent) -> Unit = {},
    navigateToBoard: (String) -> Unit,
    navigateToAddTimer: () -> Unit,
) {

    val uiState by viewModel.uiState.collectAsState(UiState())

    fun openDrawer() {
        coroutineScope.launch { drawerState.open() }
    }

    fun closeDrawer() {
        coroutineScope.launch { drawerState.close() }
    }


    ModalNavigationDrawer(
        drawerContent = {
            ModalDrawerSheet(
                drawerShape = RectangleShape
            ) {
                NavigationDrawer(
                    closeDrawer = {closeDrawer()} ,
                    onEvent = viewModel::onEvent,
                    boards = uiState.boardsWithTimers.map { boardWithTimers -> boardWithTimers.board }
                )
            }
        },
        drawerState = drawerState,
        content = {
            Scaffold(
                modifier = Modifier
                    .fillMaxSize(),
                containerColor = BackgroundDarkGray,
                topBar = {
                    TimerTopBar(
                        title = HomeDestination.title,
                        displayIcon = true,
                        iconOnclick = { openDrawer() },
                        scrollBehavior = null,
                        icon = Icons.Filled.Menu
                    )
                },
                floatingActionButton = {
                    FloatingActionButton(
                        containerColor = Color(0xFF629D61),
                        shape = RoundedCornerShape(50.dp),
                        onClick = {
                            navigateToAddTimer()
                        }
                    ) {
                        Image(
                            modifier = Modifier.size(30.dp),
                            painter = painterResource(R.drawable.timer_add),
                            contentDescription = "Add timer",
                            colorFilter = ColorFilter.tint(Color.White)
                        )

                    }
                }
            ) { innerPadding ->
                Box(modifier = Modifier.padding(innerPadding)) {
                    if (uiState.boardsWithTimers.isNotEmpty())
                        Timers(
                            timers = uiState.boardsWithTimers[uiState.currentBoard].timers,
                            onEvent = onEvent,
                        )
                }
            }
        }
    )
}