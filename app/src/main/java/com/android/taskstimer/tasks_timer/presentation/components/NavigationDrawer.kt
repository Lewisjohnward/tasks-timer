package com.android.taskstimer.tasks_timer.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.List
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.NavigationDrawerItem
import androidx.compose.material3.NavigationDrawerItemDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.android.taskstimer.core.domain.model.BoardItem
import com.android.taskstimer.tasks_timer.presentation.HomeScreenEvent
import com.android.taskstimer.core.presentation.ui.theme.BackgroundDarkGray

private data class DrawerItem(
    val text: String,
    val icon: ImageVector = Icons.Filled.CheckCircle,
    val selected: Boolean = false,
    val onClick: () -> Unit = {}
)


@Composable
private fun NavDrawerItem(
    closeDrawer: () -> Unit = {},
    item: DrawerItem,
    handle: @Composable () -> Unit = {}
) {
    Row(
        modifier = Modifier.padding(end = 16.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        NavigationDrawerItem(
            modifier = Modifier.weight(1f),
            label = { Text(text = item.text) },
            icon = { Icon(imageVector = item.icon, contentDescription = null) },
            selected = item.selected,
            shape = RoundedCornerShape(0.dp),
            onClick = {
                item.onClick()
                closeDrawer()
            },
            colors = NavigationDrawerItemDefaults.colors(
                selectedContainerColor = Color.Red,
                unselectedContainerColor = Color.Transparent,
                unselectedIconColor = Color.White,
                unselectedTextColor = Color.White,
            )
        )
        Row(modifier = Modifier.weight(0.1f)) {
            handle()
        }
    }
}

@Composable
fun NavigationDrawer(
    boards: List<BoardItem> = listOf(),
    onEvent: (HomeScreenEvent) -> Unit = {},
    closeDrawer: () -> Unit = {},
    editBoards: Boolean = true
) {

    var inputDialogVisible by remember { mutableStateOf(false) }
    ModalDrawerSheet(
        drawerShape = RectangleShape
    ) {
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
                    color = Color(0x77FFFFFF),
                    fontWeight = FontWeight.Bold,
                    fontSize = 25.sp
                )
                NavDrawerItem(
                    item = DrawerItem(
                        text = "Simple Timer",
                        onClick = {}
                    )
                )
            }
            Spacer(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(1.dp)
                    .background(Color.Gray)
            )
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    modifier = Modifier.padding(16.dp),
                    text = "Boards",
                    color = Color(0x99FFFFFF),
                    fontWeight = FontWeight.Bold
                )
                TextButton(onClick = { onEvent(HomeScreenEvent.EditBoards(!editBoards)) }) {
                    Text(
                        text = if (editBoards) "Done" else "Edit",
                        color = Color(0xFFFF9B88)
                    )
                }
            }
            LazyColumn(modifier = Modifier.weight(0.5f)) {

                itemsIndexed(boards) { index, board ->
                    NavDrawerItem(
                        closeDrawer = closeDrawer,
                        handle = {
                            if (editBoards)
                                Icon(
                                    imageVector = Icons.Filled.List,
                                    contentDescription = "Drag handle",
                                    tint = Color.White
                                )
                        },
                        item = DrawerItem(
                            text = board.name,
                            onClick = { onEvent(HomeScreenEvent.SelectBoard(index)) }
                        )
                    )
                }
                if (editBoards) item {
                    Button(
                        onClick = { inputDialogVisible = true },
                        colors = ButtonDefaults.buttonColors(
                            containerColor = Color.Transparent
                        ),
                        contentPadding = PaddingValues(10.dp)
                    ) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(5.dp)
                        ) {
                            Icon(
                                imageVector = Icons.Filled.Add,
                                contentDescription = null,
                                tint = Color(0x44FFFFFF)
                            )
                            Text(
                                text = "Add",
                                color = Color(0x44FFFFFF),
                                fontWeight = FontWeight.Bold
                            )
                        }
                    }
                }

            }
            Spacer(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(1.dp)
                    .background(Color.Gray)
            )
            NavDrawerItem(
                item = DrawerItem(
                    text = "Settings",
                    onClick = {}
                )
            )

            if (inputDialogVisible)
                InputDialog(
                    onEvent = onEvent,
                    cancel = { inputDialogVisible = false }

                )
        }
    }
}


@Preview(showBackground = true)
@Composable
fun NavigationDrawerPreview() {
    Surface(Modifier.fillMaxSize()) {
        NavigationDrawer()
    }
}