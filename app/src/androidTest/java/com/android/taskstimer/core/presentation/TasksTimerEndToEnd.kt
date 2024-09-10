package com.android.taskstimer.core.presentation

import androidx.compose.ui.test.assertTextContains
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performTextInput
import androidx.compose.ui.test.performTouchInput
import androidx.compose.ui.test.swipeLeft
import com.android.taskstimer.core.di.AppModule
import com.android.taskstimer.core.presentation.util.TestTags
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import dagger.hilt.android.testing.UninstallModules
import kotlinx.coroutines.delay
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Rule
import org.junit.Test


@HiltAndroidTest
@UninstallModules(AppModule::class)
class TasksTimerEndToEnd {

    @get:Rule(order = 0)
    var hiltRule = HiltAndroidRule(this)

    @get:Rule(order = 1)
    val composeRule = createAndroidComposeRule<MainActivity>()

    @Before
    fun setUp() {
        hiltRule.inject()
    }

    @Test
    fun tasksTimerEndToEnd() {
        // Check on fresh launch no board present
        assertBoardName("")

        // After adding timer check board Untitled present
        addTimer("Timer 1")
        runBlocking {
            delay(200)
        }
        assertTimerAdded("Timer 1")
        addTimer("Timer 2")
        runBlocking {
            delay(200)
        }
        assertTimerAdded("Timer 2")
        assertBoardName("Untitled")

        assertBoardDisplayedInTray("Untitled")
        addBoard("Board 1")
        assertBoardDisplayedInTray("Board 1")

//        composeRule.activityRule.scenario.onActivity { activity ->
//            activity.onBackPressedDispatcher.onBackPressed()
//        }
        composeRule.onNodeWithTag(TestTags.DRAWER).performTouchInput {
            swipeLeft()
        }

//        composeRule.pre

        navigateToBoard("Board 1")
        assertBoardName("Board 1")


        addBoard("Board 2")
        navigateToBoard("Board 2")
        assertBoardName("Board 2")
        addTimer("Timer 1")
        runBlocking {
            delay(300)
        }
        assertTimerAdded("Timer 1")
        assertBoardName("Board 2")

        navigateToBoard("Untitled")
        assertBoardName("Untitled")

        runBlocking {
            delay(300)
        }



//        deleteBoard()

        deleteTimer("Timer 1")
        deleteTimer("Timer 2")

        // Assert correct deletion

    }


    private fun deleteTimer(name: String){
        composeRule.onNodeWithTag("${TestTags.TIMER_MENU} $name").performClick()
        composeRule.onNodeWithTag(TestTags.TIMER_MENU_DELETE_TIMER).performClick()

    }

    // Not working correctly, when deleting the UI isn't updated quick enough
    private fun deleteBoard(){
        composeRule.onNodeWithTag(TestTags.BOARD_MENU_BUTTON).performClick()
        composeRule.onNodeWithTag(TestTags.BOARD_MENU_DELETE_BOARD).performClick()
    }

    private fun navigateToBoard(boardName: String) {
        openNavTray()
        composeRule.onNodeWithTag("${TestTags.BOARD} ${boardName}").performClick()

    }

    private fun assertBoardDisplayedInTray(boardName: String) {
        openNavTray()
        composeRule.onNodeWithTag("${TestTags.BOARD} ${boardName}").assertExists()

    }

    private fun addBoard(boardName: String){
        openNavTray()
        composeRule.onNodeWithTag(TestTags.DRAWER_EDIT_BUTTON).performClick()
        composeRule.onNodeWithTag(TestTags.DRAWER_ADD_BOARD_BUTTON).performClick()
        composeRule.onNodeWithTag(TestTags.DIALOG_ADD_BOARD_INPUT_FIELD).performTextInput(boardName)
        composeRule.onNodeWithTag(TestTags.DIALOG_ADD_BOARD_CONFIRM).performClick()
    }



    private fun addTimer(timerName: String) {
        composeRule.onNodeWithTag(TestTags.ADD_TIMER_FAB).performClick()
        composeRule.onNodeWithTag(TestTags.INPUT_FIELD).performTextInput(timerName)
        composeRule.onNodeWithTag(TestTags.SAVE_BUTTON).performClick()
    }

    private fun assertTimerAdded(timerName: String){
        composeRule.onNodeWithTag("${TestTags.TIMER} ${timerName}").assertExists()
    }

    private fun assertBoardName(boardName: String) {
        composeRule.onNodeWithTag(TestTags.TOP_APP_BAR_TEXT).assertTextContains(boardName)
    }

    private fun openNavTray() {
        composeRule.onNodeWithTag(TestTags.OPEN_DRAWER_BUTTON).performClick()
    }

//    private fun assertBoardTitleCorrect() {
//        composeRule.onNodeWithTag(TestTags.TOP_APP_BAR_TEXT).assertTextContains("Untitled")
//    }
//
//    private fun assertUntitledBoardIsAdded() {
//        composeRule.onNodeWithTag("${TestTags.BOARD} Untitled")
//            .assertIsDisplayed()
//    }
//
//    private fun assertTimerIsAddedAndVisible() {
//        composeRule.onNodeWithTag("${TestTags.TIMER} My banging new timer")
//            .assertIsDisplayed()
//    }

}