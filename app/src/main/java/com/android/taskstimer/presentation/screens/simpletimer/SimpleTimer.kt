package com.android.taskstimer.presentation.screens.simpletimer

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
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
import androidx.compose.material3.Text
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import com.android.taskstimer.R
import com.android.taskstimer.presentation.AppViewModelProvider
import com.android.taskstimer.presentation.components.NavigationDrawer
import com.android.taskstimer.presentation.components.TimerTopBar
import com.android.taskstimer.presentation.components.Timers
import com.android.taskstimer.presentation.navigation.NavigationDestination
import com.android.taskstimer.presentation.screens.home.HomeDestination
import com.android.taskstimer.presentation.screens.home.HomeScreenEvent
import com.android.taskstimer.presentation.screens.home.HomeViewModel
import com.android.taskstimer.presentation.screens.timers.TimerAddDestination
import com.android.taskstimer.ui.theme.BackgroundDarkGray
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

object SimpleTimerDestination : NavigationDestination {
    override val route = "simpleTimer"
    override val title = "Simple Timer"
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SimpleTimer(
    coroutineScope: CoroutineScope = rememberCoroutineScope(),
    drawerState: DrawerState = rememberDrawerState(initialValue = DrawerValue.Closed),
//    viewModel: HomeViewModel = viewModel(factory = AppViewModelProvider.Factory),
//    onEvent: (HomeScreenEvent) -> Unit = viewModel::onEvent,
    navController: NavController
) {


    fun openDrawer() {
        coroutineScope.launch { drawerState.open() }
    }

    ModalNavigationDrawer(
        drawerContent = {
            ModalDrawerSheet(drawerShape = RectangleShape) {
                NavigationDrawer(navController = navController)
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
            ) { innerPadding ->
                Box(modifier = Modifier.padding(innerPadding)) {
                    Text(text = "Simple timer")
                }
            }
        }
    )
}