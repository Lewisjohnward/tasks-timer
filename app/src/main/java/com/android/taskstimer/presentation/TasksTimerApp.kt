package com.android.taskstimer.presentation

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.DrawerState
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.Icon
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.Text
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.android.taskstimer.data.AppViewModel
import com.android.taskstimer.data.TasksTimer
import com.android.taskstimer.data.TasksTimerEvent
import com.android.taskstimer.presentation.screen.Timer
import com.android.taskstimer.ui.theme.DarkGray
import com.android.taskstimer.ui.theme.TasksTimerTheme
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@Composable
fun TasksTimerApp(
    coroutineScope: CoroutineScope = rememberCoroutineScope(),
    drawerState: DrawerState = rememberDrawerState(initialValue = DrawerValue.Closed),
    uiState: TasksTimer,
    onEvent: (TasksTimerEvent) -> Unit

) {
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
                drawerState = drawerState
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(DarkGray)
                ) {
                    Button(
                        onClick = { openDrawer() },
                        colors = ButtonDefaults.buttonColors(
                            containerColor = Color.Transparent,
                        )
                    ) {
                        Icon(imageVector = Icons.Filled.Menu, contentDescription = "Menu icon")
                    }


                    LazyColumn {
                        itemsIndexed(uiState.timers) { index, item ->
                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.spacedBy(2.dp)
                            ) {
                                Text(
                                    text = index.toString(),
                                    fontSize = 10.sp,
                                    fontWeight = FontWeight(100),
                                    color = Color.White
                                )
                                Text(
                                    text = item.remainingTime,
                                    fontSize = 25.sp,
                                    color = Color.White
                                )
                            }

                        }

                    }
                    Button(onClick = { onEvent(TasksTimerEvent.StartTimer) }) {

                    }
                }
            }
        }
    }
}

@Composable
fun NavigationDrawer() {
    Column(
        modifier = Modifier
            .fillMaxWidth(0.8f)
            .fillMaxHeight()
            .background(Color(0xFF536878))
    ) {
        Button(
            onClick = { /*TODO*/ },
            colors = ButtonDefaults.buttonColors(
                containerColor = Color.Red
            ),
            shape = RoundedCornerShape(0.dp)
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(imageVector = Icons.Filled.CheckCircle, contentDescription = null)
                Text(text = "Stopwatch")
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun TasksTimerAppPreview() {
    val viewModel = AppViewModel()
    val uiState by viewModel.uiState.collectAsState()
    TasksTimerApp(uiState = uiState, onEvent = viewModel::onEvent)
}