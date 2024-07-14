package com.android.taskstimer.presentation.components

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.rounded.MoreVert
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.TopAppBarScrollBehavior
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import com.android.taskstimer.ui.theme.BackgroundDarkGray

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TimerTopBar(
    title: String = "Top bar",
    displayIcon: Boolean = true,
    scrollBehavior: TopAppBarScrollBehavior? = null,
    iconOnclick: () -> Unit = {},
    icon: ImageVector = Icons.Filled.ArrowBack,
    actionIcon: ImageVector? = null,
    actionOnClick: () -> Unit = {}
) {
    CenterAlignedTopAppBar(
        title = {
            Text(
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
            actionIcon?.let {
                IconButton(onClick = actionOnClick) {
                    Icon(
                        imageVector = it,
                        contentDescription = null
                    )
                }
            }
        },
        scrollBehavior = scrollBehavior,
        navigationIcon = {
            if (displayIcon) {
                IconButton(onClick = iconOnclick) {
                    Icon(
                        imageVector = icon,
                        contentDescription = "Back button"
                    )
                }
            }
        }
    )

}


@OptIn(ExperimentalMaterial3Api::class)
@Preview(showBackground = true)
@Composable
fun TimerTopBarPreview() {
    TimerTopBar()
}