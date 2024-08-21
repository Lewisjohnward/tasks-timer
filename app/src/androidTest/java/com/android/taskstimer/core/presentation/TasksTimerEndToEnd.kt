package com.android.taskstimer.core.presentation

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.assertTextContains
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.performClick
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
        assertNoBoardOnStartup()
        navigateToAddTimer()
        addTimer()
        runBlocking {
            delay(300)
        }
        openBoardsTray()
        runBlocking {
            delay(300)
        }
        assertUntitledBoardIsAdded()
//        composeRule.onNodeWithTag("${TestTags.BOARD} Untitled")
//            .performClick()
        runBlocking {
            delay(300)
        }
        assertTimerIsAddedAndVisible()



//        assertBoardTitleCorrect()
    }



    private fun navigateToAddTimer() {
        composeRule.onNodeWithTag(TestTags.ADD_TIMER_FAB).performClick()
    }

    private fun addTimer() {
        composeRule.onNodeWithTag(TestTags.SAVE_BUTTON).performClick()
    }

    private fun assertNoBoardOnStartup() {
        composeRule.onNodeWithTag(TestTags.TOP_APP_BAR_TEXT).assertTextContains("")
    }

    private fun openBoardsTray() {
        composeRule.onNodeWithTag(TestTags.MENU_BUTTON).performClick()
    }

    private fun assertBoardTitleCorrect() {
        composeRule.onNodeWithTag(TestTags.TOP_APP_BAR_TEXT).assertTextContains("Untitled")
    }

    private fun assertUntitledBoardIsAdded() {
        composeRule.onNodeWithTag("${TestTags.BOARD} Untitled")
            .assertIsDisplayed()
    }

    private fun assertTimerIsAddedAndVisible() {
        composeRule.onNodeWithTag("${TestTags.TIMER} My banging new timer")
            .assertIsDisplayed()
    }

}