package com.android.taskstimer.tasks_timer.presentation.components

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.TopAppBarScrollBehavior
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.android.taskstimer.core.presentation.ui.theme.BackgroundDarkGray
import com.android.taskstimer.core.presentation.ui.theme.TopBarShadow
import com.android.taskstimer.core.presentation.util.TestTags
import com.android.taskstimer.core.presentation.util.shadow

@Composable
fun TimerTopBar(
    title: String = "Top bar",
    displayIcon: Boolean = true,
    scrollBehavior: TopAppBarScrollBehavior? = null,
    iconOnclick: () -> Unit = {},
    icon: ImageVector = Icons.AutoMirrored.Filled.ArrowBack,
    actionIcon: ImageVector? = null,
    actionOnClick: () -> Unit = {},
    actionEnabled: Boolean = true,
    menuEnabled: Boolean = false
) {

    val animatedAlpha by animateFloatAsState(
        targetValue = if (menuEnabled) 1f else 0.3f,
    )


    CenterAlignedTopAppBar(
        modifier = Modifier.shadow(
            color = TopBarShadow,
            offsetX = (1).dp,
            offsetY = (1).dp,
            blurRadius = 8.dp,
        ),
        title = {
            Text(
                modifier = Modifier.testTag(TestTags.TOP_APP_BAR_TEXT),
                text = title,
                fontSize = 25.sp,
                textAlign = TextAlign.Center
            )
        },
        colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
            containerColor = BackgroundDarkGray,
            navigationIconContentColor = Color.White,
            titleContentColor = Color.White,
            actionIconContentColor = Color.White

        ),
        actions = {
            if (actionEnabled) {
                actionIcon?.let {
                    IconButton(
                        modifier = Modifier.testTag(TestTags.BOARD_MENU_BUTTON),
                        onClick = actionOnClick,
                        enabled = menuEnabled
                    ) {
                        Icon(
                            imageVector = it,
                            contentDescription = null,
                            tint = if (menuEnabled) Color.White else Color.White.copy(alpha = animatedAlpha)
                        )
                    }
                }
            }
        },
        scrollBehavior = scrollBehavior,
        navigationIcon = {
            if (displayIcon) {
                IconButton(
                    modifier = Modifier.testTag(TestTags.OPEN_DRAWER_BUTTON),
                    onClick = iconOnclick,
                    enabled = menuEnabled
                ) {
                    Icon(
                        imageVector = icon,
                        contentDescription = "Back button",
                        tint = if (menuEnabled) Color.White else Color.White.copy(alpha = animatedAlpha)
                    )
                }
            }
        }
    )

}


@Preview(showBackground = true)
@Composable
fun TimerTopBarPreview() {
    TimerTopBar()
}