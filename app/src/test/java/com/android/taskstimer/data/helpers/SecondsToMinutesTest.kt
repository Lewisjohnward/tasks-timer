package com.android.taskstimer.data.helpers

import com.android.taskstimer.data.Timer
import com.android.taskstimer.presentation.screens.home.formatTime
import org.junit.Assert
import org.junit.Test

class SecondsToMinutesTest {
    @Test
    fun formatTest1() {
        val timer = Timer(name = "test", presetTime = "70")

        // Expected, Actual
        Assert.assertEquals("01:10", timer.formatTime())
    }
    @Test
    fun formatTest2() {
        val timer = Timer(name = "test", presetTime = "45")
        // Expected, Actual
        Assert.assertEquals("00:45", timer.formatTime())
    }
    @Test
    fun formatTest3() {
        val timer = Timer(name = "test", presetTime = "10")
        // Expected, Actual
        Assert.assertEquals("00:10", timer.formatTime())
    }
    @Test
    fun formatTest4() {
        val timer = Timer(name = "test", presetTime = "1225")
        // Expected, Actual
        Assert.assertEquals("20:25", timer.formatTime())
    }
    @Test
    fun formatTest5() {
        val timer = Timer(name = "test", presetTime = "65")
        // Expected, Actual
        Assert.assertEquals("01:05", timer.formatTime())
    }
}