package com.android.taskstimer.presentation.screens.board

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.android.taskstimer.presentation.AppViewModelProvider
import com.android.taskstimer.presentation.components.Timers
import com.android.taskstimer.presentation.navigation.NavigationDestination

object BoardDestination : NavigationDestination {
    override val route = "board"
    override val title = "board"
    const val boardName = "boardName"
    val routeWithArgs = "$route/{$boardName}"
}

@Composable
fun Board(
    viewModel: BoardViewModel = viewModel(factory = AppViewModelProvider.Factory)
) {
    val uiState = viewModel.uiState.collectAsState()
    println(uiState.value.timers)
    LazyColumn {
        items(uiState.value.timers) { timer ->
            Text( text = timer.name)
        }
    }
}