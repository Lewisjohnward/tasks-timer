package com.android.taskstimer.core.local

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.test.filters.SmallTest
import com.android.taskstimer.core.data.local.TasksTimerDatabase
import com.android.taskstimer.core.data.local.timer.TimerDao
import com.android.taskstimer.core.di.AppModule
import com.google.common.truth.Truth.assertThat
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import dagger.hilt.android.testing.UninstallModules
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import javax.inject.Inject

@HiltAndroidTest
@SmallTest
@UninstallModules(AppModule::class)
class NoteDaoTest {

    @get:Rule
    val hiltRule = HiltAndroidRule(this)

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    @Inject
    lateinit var tasksTimerDb: TasksTimerDatabase
    private lateinit var timerDao: TimerDao

    @Before
    fun setUp() {
        hiltRule.inject()
        timerDao = tasksTimerDb.timerDao
    }

    @After
    fun tearDown() {
        tasksTimerDb.close()
    }

    @Test
    fun getAllNotesFromEmptyDb_noteListIsEmpty() = runTest {
        assertThat(
            timerDao.getAllTimers().first().isEmpty()
        )
    }
}