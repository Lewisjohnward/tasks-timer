package com.android.taskstimer.timer

import com.android.taskstimer.timer.presentation.components.Side
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject
import javax.inject.Singleton


data class InputState(
    val focus: Boolean = false,
    val value: Int = 0,
    val unit: String,
    val side: Side,
    val wipeOnInput: Boolean = false
)

fun InputState.displayValue(): String {
    return if (value < 10) {
        "0$value"
    } else "$value"
}

val timeInput = listOf(
    InputState(unit = "H", side = Side.LEFT),
    InputState(focus = true, unit = "M", side = Side.MIDDLE),
    InputState(unit = "S", side = Side.RIGHT),
)

enum class Carry {
    INCREMENT,
    DECREMENT
}


@Singleton
class TimerStateManager @Inject constructor() {

    val state = MutableStateFlow(timeInput)

    fun changeFocus(side: Side) {
        state.update { prevState ->
            prevState.map { input ->
                if (side == input.side) input.copy(
                    focus = true,
                    wipeOnInput = true
                )
                else input.copy(focus = false)
            }

        }
    }

    fun increment() {
        state.update { prevState ->
            val updatedValues = prevState.map { input ->
                if (input.focus) {
                    input.copy(value = input.value + 1)
                } else input
            }
            calculateCarryValues(updatedValues)
        }
    }

    fun decrement() {
        state.update { prevState ->
            val updatedValues = prevState.map { input ->
                if (input.focus) {
                    input.copy(value = input.value - 1)
                } else input
            }
            calculateCarryValues(updatedValues)
        }
    }

    fun inputValue(value: Int) {
        var focusInput: Int? = null
        state.update { prevState ->
            val updatedValues = prevState.mapIndexed map@{ index, it ->

                if (!it.focus) return@map it

                if (it.value == 0 || it.wipeOnInput) return@map it.copy(
                    value = value,
                    wipeOnInput = false
                )

                if (it.value / 10 == 0) {
                    if (index < prevState.size - 1) focusInput = index + 1
                    return@map it.copy(
                        value = ((it.value * 10) + value),
                    )
                }

                it.copy(value = value)
            }

            val carriedValues = calculateCarryValues(updatedValues)

            val updatedFocus =
                if (focusInput != null) {
                    carriedValues.mapIndexed map@{ index, it ->
                        if (index != focusInput) return@map it.copy(focus = false)
                        it.copy(focus = true)
                    }
                } else carriedValues

            updatedFocus
        }
    }

    fun getInputValueAsSeconds(): Int{
        val sum = state.value.reduce{ acc, el ->
         return acc.value + el.value
        }.value
        return sum
    }


    fun setTime(timeInSeconds: Int) {
        val hours = timeInSeconds / 3600
        val minutes = (timeInSeconds % 3600) / 60
        val seconds = timeInSeconds % 60

        state.update {
            it.mapIndexed map@{ index, inputState ->
                if(index == 0) return@map inputState.copy(value = hours)
                if(index == 1) return@map inputState.copy(
                    value = minutes,
                    wipeOnInput = true
                )
                if(index == 2) return@map inputState.copy(value = seconds)
                inputState
            }
        }
    }

    private fun calculateCarryValues(values: List<InputState>): List<InputState> {
        var carryValue: Carry? = null
        val updatedValues = values.reversed().map {
            if (it.value >= 60) {
                carryValue = Carry.INCREMENT
                return@map it.copy(value = it.value - 60)
            }
            if (it.value < 0) {
                carryValue = Carry.DECREMENT
                return@map it.copy(value = 60 + it.value)
            }
            if (carryValue == Carry.INCREMENT) {
                carryValue = null
                return@map it.copy(value = it.value + 1)
            }
            if (carryValue == Carry.DECREMENT) {
                carryValue = null
                return@map it.copy(value = it.value - 1)
            }
            it
        }.reversed()
        return updatedValues
    }


    // TODO: THIS SHOULD GO IN VIEWMODEL INSTEAD?
    fun add() {
        println("Add")
    }

    fun delete() {
        state.update { prevState ->
            prevState.map {
                if (it.focus) it.copy(value = 0) else it
            }

        }
    }
}