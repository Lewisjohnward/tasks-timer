package com.android.taskstimer.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationDrawerItem
import androidx.compose.material3.NavigationDrawerItemDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.android.taskstimer.presentation.screens.simpletimer.SimpleTimerDestination
import com.android.taskstimer.ui.theme.BackgroundDarkGray

private data class DrawerItem(
    val text: String,
    val icon: ImageVector = Icons.Filled.CheckCircle,
    val selected: Boolean = false,
    val onClick: () -> Unit = {}
)


@Composable
private fun NavDrawerItem(
    item: DrawerItem
) {
    NavigationDrawerItem(
        label = { Text(text = item.text) },
        icon = { Icon(imageVector = item.icon, contentDescription = null) },
        selected = item.selected,
        shape = RoundedCornerShape(0.dp),
        onClick = { item.onClick() },
        colors = NavigationDrawerItemDefaults.colors(
            selectedContainerColor = Color.Red,
            unselectedContainerColor = Color.Transparent,
            unselectedIconColor = Color.White,
            unselectedTextColor = Color.White,
        )
    )
}

@Composable
fun NavigationDrawer(navController: NavController) {
    Column(
        modifier = Modifier
            .fillMaxWidth(0.8f)
            .fillMaxHeight()
            .background(BackgroundDarkGray),
        verticalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        Column {
            Text(
                modifier = Modifier.padding(16.dp),
                text = "Menu",
                color = Color(0x99FFFFFF),
                fontWeight = FontWeight.Bold
            )
            NavDrawerItem(item = DrawerItem(text = "Simple Timer", onClick = {navController.navigate(SimpleTimerDestination.route)}))
        }
        Spacer(
            modifier = Modifier
                .fillMaxWidth()
                .height(1.dp)
                .background(Color.Gray)
        )
        Column {
            Text(
                modifier = Modifier.padding(16.dp),
                text = "Boards",
                color = Color(0x99FFFFFF),
                fontWeight = FontWeight.Bold
            )
            NavDrawerItem(item = DrawerItem(text = "Basic Productivity", onClick = {}))
        }
        Spacer(
            modifier = Modifier
                .fillMaxWidth()
                .height(1.dp)
                .background(Color.Gray)
        )
    }
}

