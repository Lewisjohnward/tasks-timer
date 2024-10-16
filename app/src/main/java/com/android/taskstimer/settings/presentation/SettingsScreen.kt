package com.android.taskstimer.settings.presentation

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ChevronLeft
import androidx.compose.material3.BasicAlertDialog
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LocalMinimumInteractiveComponentEnforcement
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.android.taskstimer.R
import com.android.taskstimer.core.presentation.navigation.NavigationDestination
import com.android.taskstimer.core.presentation.ui.theme.BackgroundDarkGray
import com.android.taskstimer.core.presentation.ui.theme.Green
import com.android.taskstimer.core.presentation.ui.theme.TasksTimerTheme
import com.android.taskstimer.tasks_timer.presentation.components.TimerTopBar

object SettingsDestination : NavigationDestination {
    override val route = "settings"
    override val title = "Settings"
}

@Composable
fun SettingsScreen(navigateBack: () -> Unit) {
    SettingsScreenContent(
        navigateBack = navigateBack
    )
}

@Composable
private fun SettingsScreenContent(
    navigateBack: () -> Unit = {},
    viewModel: SettingsViewModel = hiltViewModel()
) {
    var displayDialog: Boolean by remember { mutableStateOf(false) }
    val uiState: SettingsUiState by viewModel.uiState.collectAsState(SettingsUiState())
    val onEvent = viewModel::onEvent

    Scaffold(
        topBar = {
            TimerTopBar(
                title = "Settings",
                iconOnclick = navigateBack,
                menuEnabled = false
            )
        },
        containerColor = BackgroundDarkGray
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(20.dp),
            verticalArrangement = Arrangement.spacedBy(20.dp)
        ) {
            SettingsItem(
                text = stringResource(R.string.ignore_silent_mode),
                description = stringResource(R.string.ignore_silent_mode_description),
                input = {
                    Switch(
                        onClick = { onEvent(SettingsEvent.SetIgnoreSilentMode(it)) },
                        checked = uiState.ignoreSilentMode
                    )
                },
            )
            SettingsItem(
                text = stringResource(R.string.time_interval_between_timers),
                description = stringResource(R.string.time_interval_between_timers_description),
                input = {
                    Time(
                        onClick = { displayDialog = true },
                        value = uiState.timeIntervalBetweenTimers.toString()
                    )
                }
            )
            SettingsItem(
                text = stringResource(R.string.use_media_volume_when_headphones_are_connected),
                description = stringResource(R.string.use_media_volume_when_headphones_are_connected_description),
                input = {
                    Switch(
                        onClick = { onEvent(SettingsEvent.SetUseMediaVolumeHeadphones(it)) },
                        checked = uiState.useMediaVolumeHeadphones
                    )
                }
            )
        }
        if (displayDialog) {
            NumberPickerDialog(
                dismiss = { displayDialog = false },
                value = uiState.timeIntervalBetweenTimers,
                onChange = { onEvent(SettingsEvent.SetTimeInterval(it)) },
                max = 99,
                min = 0
            )
        }
    }
}


@Composable
private fun NumberPickerDialog(
    dismiss: () -> Unit,
    value: Int,
    onChange: (Int) -> Unit,
    max: Int,
    min: Int
) {

    val displayIncrement: Boolean = value < max
    val displayDecrement: Boolean = value - 1 >= min


    CompositionLocalProvider(value = LocalMinimumInteractiveComponentEnforcement provides false) {
        BasicAlertDialog(
            onDismissRequest = { dismiss() }
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(50.dp))
                    .background(Color(0xFFFFFFFF))
            ) {
                Column(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalAlignment = Alignment.CenterHorizontally,
                ) {
                    Box(modifier = Modifier.size(100.dp)) {
                        if (displayIncrement) {
                            IconButton(
                                modifier = Modifier.fillMaxSize(),
                                onClick = { onChange(value + 1) }) {
                                Icon(
                                    modifier = Modifier
                                        .rotate(90f)
                                        .fillMaxSize(),
                                    imageVector = Icons.Filled.ChevronLeft,
                                    contentDescription = "Increase",
                                )

                            }
                        }
                    }
                    Text(
                        text = "${value}s",
                        fontSize = 75.sp,
                    )
                    Box(modifier = Modifier.size(100.dp)) {
                        if (displayDecrement) {
                            IconButton(
                                modifier = Modifier
                                    .fillMaxSize(),
                                onClick = { onChange(value - 1) }
                            ) {
                                Icon(
                                    modifier = Modifier
                                        .rotate(-90f)
                                        .fillMaxSize(),
                                    imageVector = Icons.Filled.ChevronLeft,
                                    contentDescription = "Decrease"
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}


@Composable
private fun SettingsItem(
    text: String,
    description: String,
    input: @Composable () -> Unit
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Column(
            modifier = Modifier.weight(0.5f),
            verticalArrangement = Arrangement.spacedBy(2.dp)
        ) {
            Text(
                text = text,
                fontSize = 18.sp,
                color = Color.White,
            )
            Text(
                text = description,
                fontSize = 14.sp,
                color = Color.White.copy(alpha = 0.5f),
            )
        }
        input()
    }
}

@Composable
private fun ColumnScope.Switch(
    onClick: (Boolean) -> Unit,
    checked: Boolean
) {
    Switch(
        modifier = Modifier.weight(0.1f),
        checked = checked,
        onCheckedChange = { onClick(it) },
        colors = SwitchDefaults.colors(
            checkedTrackColor = Green
        )
    )
}

@Composable
private fun ColumnScope.Time(
    onClick: () -> Unit,
    value: String
) {
    TextButton(
        modifier = Modifier.weight(0.1f),
        onClick = { onClick() },
    ) {
        Row(
            verticalAlignment = Alignment.Bottom
        ) {
            Text(
                text = value,
                fontSize = 30.sp,
                color = Color.White
            )
            Text(
                text = "s",
                fontSize = 15.sp,
                color = Color.White
            )

        }
    }
}


@Preview(showSystemUi = true)
@Composable
fun SettingsScreenPreview() {
    TasksTimerTheme {
        SettingsScreenContent()
    }
}