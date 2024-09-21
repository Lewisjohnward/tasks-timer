package com.android.taskstimer.core.presentation.ui

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.DirectionsRun
import androidx.compose.material.icons.automirrored.filled.MenuBook
import androidx.compose.material.icons.filled.AvTimer
import androidx.compose.material.icons.filled.Bed
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.CleaningServices
import androidx.compose.material.icons.filled.Headphones
import androidx.compose.material.icons.filled.HourglassTop
import androidx.compose.material.icons.filled.Kitchen
import androidx.compose.material.icons.filled.Pets
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.filled.Tv
import androidx.compose.ui.graphics.vector.ImageVector

enum class IconKey(val icon: ImageVector) {
    CLEANING(Icons.Filled.CleaningServices),
    HEADPHONES(Icons.Filled.Headphones),
    TV(Icons.Filled.Tv),
    EXERCISE(Icons.AutoMirrored.Filled.DirectionsRun),
    BOOK(Icons.AutoMirrored.Filled.MenuBook),
    BED(Icons.Filled.Bed),
    COOKING(Icons.Filled.Kitchen),
    HOURGLASS(Icons.Filled.HourglassTop),
    PETS(Icons.Filled.Pets),
    DEFAULT(Icons.Filled.CheckCircle),
}

val settingsIcon = Icons.Filled.Settings
val timerIcon = Icons.Filled.AvTimer


