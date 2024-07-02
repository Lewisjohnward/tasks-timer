package com.android.taskstimer.data.helpers

import org.junit.Assert
import org.junit.Test

class SecondsToMinutesTest {
    @Test
    fun formatTest1() {
        val displayTime = secondsToMinutes(70)
        // Expected, Actual
        Assert.assertEquals("01:10", displayTime)
    }
    @Test
    fun formatTest2() {
        val displayTime = secondsToMinutes(45)
        // Expected, Actual
        Assert.assertEquals("00:45", displayTime)
    }
    @Test
    fun formatTest3() {
        val displayTime = secondsToMinutes(10)
        // Expected, Actual
        Assert.assertEquals("00:10", displayTime)
    }
    @Test
    fun formatTest4() {
        val displayTime = secondsToMinutes(1225)
        // Expected, Actual
        Assert.assertEquals("20:25", displayTime)
    }
    @Test
    fun formatTest5() {
        val displayTime = secondsToMinutes(65)
        // Expected, Actual
        Assert.assertEquals("01:05", displayTime)
    }
}