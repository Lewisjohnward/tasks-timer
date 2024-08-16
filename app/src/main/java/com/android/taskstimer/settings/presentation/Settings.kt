package com.android.taskstimer.settings.presentation

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.android.taskstimer.core.presentation.navigation.NavigationDestination
import com.android.taskstimer.core.presentation.ui.theme.BackgroundDarkGray

object SettingsDestination : NavigationDestination {
    override val route = "settings"
    override val title = "Settings"
}

@Composable
fun SettingsScreen(navigateBack: () -> Boolean) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(BackgroundDarkGray),
    ) {
        Text(text = "settings page")

    }
}