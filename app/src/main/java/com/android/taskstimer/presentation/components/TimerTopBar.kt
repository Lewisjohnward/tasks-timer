package com.android.taskstimer.presentation.components

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarScrollBehavior
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TimerTopBar(
    modifier: Modifier = Modifier,
    title: String = "Top bar",
    displayIcon: Boolean = true,
    scrollBehavior: TopAppBarScrollBehavior? = null,
    iconOnclick: () -> Unit = {},
    icon: ImageVector = Icons.Filled.ArrowBack
) {
    CenterAlignedTopAppBar(
        title = {
            Text(
                text = title,
                fontSize = 25.sp
            )
        },
        modifier = modifier,
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