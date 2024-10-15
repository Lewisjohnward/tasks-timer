package com.android.taskstimer.tasks_timer.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.List
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.NavigationDrawerItem
import androidx.compose.material3.NavigationDrawerItemDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.android.taskstimer.core.domain.model.BoardItem
import com.android.taskstimer.core.presentation.ui.settingsIcon
import com.android.taskstimer.core.presentation.ui.theme.BackgroundDarkGray
import com.android.taskstimer.core.presentation.ui.timerIcon
import com.android.taskstimer.core.presentation.util.TestTags
import com.android.taskstimer.tasks_timer.presentation.CreateBoardDialog
import com.android.taskstimer.tasks_timer.presentation.HomeScreenEvent
import com.android.taskstimer.tasks_timer.presentation.NewBoardDetails
import com.android.taskstimer.tasks_timer.presentation.components.board.IconInputDialog
import com.android.taskstimer.tasks_timer.presentation.components.board.NameInputDialog
import com.android.taskstimer.tasks_timer.presentation.components.dialog.Dialog

private data class DrawerItem(
    val text: String,
    val icon: ImageVector,
    val selected: Boolean = false,
    val onClick: () -> Unit = {}
)

@Composable
fun NavigationDrawer(
    boards: List<BoardItem> = listOf(),
    onEvent: (HomeScreenEvent) -> Unit = {},
    navigateToSettings: () -> Unit = {},
    closeDrawer: () -> Unit = {},
    editBoards: Boolean = true,
    createBoard: CreateBoardDialog?,
    newBoardDetails: NewBoardDetails,
) {


    ModalDrawerSheet(
        modifier = Modifier.testTag(TestTags.DRAWER),
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
                        onClick = {},
                        icon = timerIcon
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
                TextButton(
                    modifier = Modifier.testTag(TestTags.DRAWER_EDIT_BUTTON),
                    onClick = { onEvent(HomeScreenEvent.EditBoards(!editBoards)) }
                ) {
                    Text(
                        text = if (editBoards) "Done" else "Edit",
                        color = Color(0xFFFF9B88)
                    )
                }
            }
            LazyColumn(modifier = Modifier.weight(0.5f)) {

                itemsIndexed(boards) { index, board ->
                    val drawerItem = DrawerItem(
                        text = board.name,
                        onClick = {
                            onEvent(
                                HomeScreenEvent.SelectBoard(
                                    boardIndex = index,
                                    boardId = board.id
                                )
                            )
                        },
                        icon = board.iconKey.icon
                    )

                    NavDrawerItem(
                        modifier = Modifier.testTag("${TestTags.BOARD} ${board.name}"),
                        closeDrawer = closeDrawer,
                        dragHandle = {
                            if (editBoards)
                                Icon(
                                    imageVector = Icons.AutoMirrored.Filled.List,
                                    contentDescription = "Drag handle",
                                    tint = Color.White
                                )
                        },
                        item = drawerItem
                    )
                }
                if (editBoards) item {
                    Button(
                        modifier = Modifier.testTag(TestTags.DRAWER_ADD_BOARD_BUTTON),
                        onClick = { onEvent(HomeScreenEvent.CreateNewBoard) },
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
                    onClick = { navigateToSettings() },
                    icon = settingsIcon
                )
            )

            if (createBoard != null) {
                Dialog {
                    if (createBoard == CreateBoardDialog.NAME_BOARD) {
                        NameInputDialog(
                            onEvent = onEvent,
                            confirmEnabled = false,
                            newBoardName = newBoardDetails.name
                        )
                    }
                    if (createBoard == CreateBoardDialog.CHOOSE_ICON) {
                        IconInputDialog(
                            selectedIcon = newBoardDetails.iconKey,
                            onEvent = onEvent
                        )
                    }
                }
            }

        }
    }
}

@Composable
private fun NavDrawerItem(
    modifier: Modifier = Modifier,
    closeDrawer: () -> Unit = {},
    item: DrawerItem,
    dragHandle: @Composable () -> Unit = {}
) {
    Row(
        modifier = Modifier.padding(end = 16.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        NavigationDrawerItem(
            modifier = modifier.weight(1f),
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
            dragHandle()
        }
    }
}


//@Preview(showBackground = true)
//@Composable
//fun NavigationDrawerPreview() {
//    Surface(Modifier.fillMaxSize()) {
//        NavigationDrawer(tasksTimerService = tasksTimerService)
//    }
//}