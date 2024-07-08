package com.android.taskstimer.presentation.screens.home

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
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
import androidx.compose.ui.graphics.RectangleShape
import com.android.taskstimer.presentation.components.NavigationDrawer
import com.android.taskstimer.presentation.components.Timers
import com.android.taskstimer.ui.theme.BackgroundDarkGray
import com.android.taskstimer.ui.theme.TasksTimerTheme
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import androidx.lifecycle.viewmodel.compose.viewModel
import com.android.taskstimer.presentation.AppViewModelProvider
import com.android.taskstimer.presentation.components.TimerTopBar
import com.android.taskstimer.presentation.navigation.NavigationDestination


object HomeDestination : NavigationDestination {
    override val route = "home"
    override val title = "Tasks Timer Home"
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    coroutineScope: CoroutineScope = rememberCoroutineScope(),
    drawerState: DrawerState = rememberDrawerState(initialValue = DrawerValue.Closed),
    viewModel: HomeViewModel = viewModel(factory = AppViewModelProvider.Factory),
    onEvent: (HomeScreenEvent) -> Unit = viewModel::onEvent,
    fabOnClick: () -> Unit
) {

    val uiState by viewModel.uiState.collectAsState()
    fun openDrawer() {
        coroutineScope.launch { drawerState.open() }
    }
    TasksTimerTheme {
        Column {
            ModalNavigationDrawer(
                drawerContent = {
                    ModalDrawerSheet(drawerShape = RectangleShape) {
                        NavigationDrawer()
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
                            FloatingActionButton(onClick = { fabOnClick() }) {

                            }
//                            IconButton(
//                                modifier = Modifier.background(Color.Blue),
//                                onClick = { /*TODO*/ },
//                            ) {
//                                Icon(
//                                    imageVector = Icons.Filled.CheckCircle,
//                                    contentDescription = "Add timer"
//                                )
//                            }
                        }
                    ) { innerPadding ->
                        Box(modifier = Modifier.padding(innerPadding)) {
                            Timers(
                                uiState = uiState,
                                onEvent = onEvent,
                                openDrawer = { openDrawer() }
                            )
                        }
                    }
                }
            )
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