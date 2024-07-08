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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.android.taskstimer.ui.theme.BackgroundDarkGray

@Composable
fun NavigationDrawer() {
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
            NavigationDrawerItem(
                label = { Text(text = "Stopwatch") },
                icon = { Icon(imageVector = Icons.Filled.CheckCircle, contentDescription = null) },
                selected = false,
                shape = RoundedCornerShape(0.dp),
                onClick = { /*TODO*/ },
                colors = NavigationDrawerItemDefaults.colors(
                    selectedContainerColor = Color.Red,
                    unselectedContainerColor = Color.Transparent,
//                selectedIconColor =,
                    unselectedIconColor = Color.White,
//                selectedTextColor =,
                    unselectedTextColor = Color.White,
//                selectedBadgeColor =,
//                unselectedBadgeColor =
                )
            )
            NavigationDrawerItem(
                label = { Text(text = "Simple Timer") },
                icon = { Icon(imageVector = Icons.Filled.CheckCircle, contentDescription = null) },
                selected = true,
                shape = RoundedCornerShape(0.dp),
                onClick = { /*TODO*/ }
            )
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
            NavigationDrawerItem(
                label = { Text(text = "Basic productivity") },
                icon = { Icon(imageVector = Icons.Filled.CheckCircle, contentDescription = null) },
                selected = false,
                shape = RoundedCornerShape(0.dp),
                onClick = { /*TODO*/ },
                colors = NavigationDrawerItemDefaults.colors(
                    selectedContainerColor = Color.Red,
                    unselectedContainerColor = Color.Transparent,
//                selectedIconColor =,
                    unselectedIconColor = Color.White,
//                selectedTextColor =,
                    unselectedTextColor = Color.White,
//                selectedBadgeColor =,
//                unselectedBadgeColor =
                )
            )
        }
        Spacer(
            modifier = Modifier
                .fillMaxWidth()
                .height(1.dp)
                .background(Color.Gray)
        )
    }
}

