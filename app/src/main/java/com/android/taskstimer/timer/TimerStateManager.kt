package com.android.taskstimer.timer

import com.android.taskstimer.timer.presentation.components.Side
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject
import javax.inject.Singleton


data class InputState(
    val inFocus: Boolean = false,
    val value: Int = 0,
    val unit: String,
    val side: Side,
    val valuesReceived: Int = 0,
    val wipeOnInput: Boolean = false
)

fun InputState.displayValue(): String {
    return if (value < 10) {
        "0$value"
    } else "$value"
}

val timeInput = listOf(
    InputState(unit = "H", side = Side.LEFT),
    InputState(inFocus = true, unit = "M", side = Side.MIDDLE),
    InputState(unit = "S", side = Side.RIGHT),
)

enum class Carry {
    INCREMENT,
    DECREMENT
}


@Singleton
class TimerStateManager @Inject constructor() {


    // TODO: SEPARATE OUT THE TIME COMPONENTS AND THEN COMBINE
//    val _seconds = MutableStateFlow(0)
//    val _minutes = MutableStateFlow(0)
//    val _hours = MutableStateFlow(0)
    // val _uiState = ??


    val state = MutableStateFlow(timeInput)

    fun changeFocus(side: Side) {
        state.update { prevState ->
            prevState.map { input ->
                if (side == input.side) input.copy(
                    inFocus = true,
                    wipeOnInput = true,
                    valuesReceived = 0
                )
                else input.copy(
                    inFocus = false,
                    valuesReceived = 0
                )
            }

        }
    }

    fun increment() {
        state.update { prevState ->
            val updatedValues = prevState.map { input ->
                if (input.inFocus) {
                    input.copy(value = input.value + 1)
                } else input
            }
            calculateCarryValues(updatedValues)
        }
    }

    fun decrement() {
        state.update { prevState ->
            val updatedValues = prevState.map { input ->
                if (input.inFocus && input.value != 0) {
                    input.copy(value = input.value - 1)
                } else input
            }
            calculateCarryValues(updatedValues)
        }
    }

    fun inputValue(value: Int) {
        var focusInput: Int? = null
        state.update { prevState ->
            val updatedValues = prevState.mapIndexed map@{ index, inputState ->

                if (!inputState.inFocus) return@map inputState
                val updatedState = inputState.copy(valuesReceived =  inputState.valuesReceived + 1)


                if (inputState.value == 0 || inputState.wipeOnInput) return@map updatedState.copy(
                    value = value,
                    wipeOnInput = false
                )

                if (inputState.value / 10 == 0) {
                    if (index < prevState.size - 1) focusInput = index + 1
                    return@map updatedState.copy(
                        value = ((inputState.value * 10) + value),
                    )
                }
                updatedState.copy(value = value)
            }

            // Handle the case of input "01"
            updatedValues.forEachIndexed map@{ index, inputState ->
                if(!inputState.inFocus) return@map
                if(inputState.valuesReceived == 2){
                    if (index < prevState.size - 1) focusInput = index + 1
                }
            }


            val carriedValues = calculateCarryValues(updatedValues)

            val updatedFocus =
                if (focusInput != null) {
                    carriedValues.mapIndexed map@{ index, it ->
                        if (index != focusInput) return@map it.copy(inFocus = false, valuesReceived = 0)
                        it.copy(inFocus = true, valuesReceived = 0)
                    }
                } else carriedValues

            updatedFocus
        }
    }


    fun getInputValueAsSeconds(): String {
        var timeInSeconds = 0
        state.value.forEach { inputState ->
            if (inputState.side == Side.LEFT) {
                val time = 60 * 60 * inputState.value
                timeInSeconds += time
                return@forEach
            }
            if (inputState.side == Side.MIDDLE) {
                val time = 60 * inputState.value
                timeInSeconds += time
                return@forEach
            }
            if (inputState.side == Side.RIGHT) {
                val time = inputState.value
                timeInSeconds += time
                return@forEach
            }
        }
        return timeInSeconds.toString()
    }


    fun setTime(timeInSeconds: Int) {
        val hours = timeInSeconds / 3600
        val minutes = (timeInSeconds % 3600) / 60
        val seconds = timeInSeconds % 60

        state.update {
            it.mapIndexed map@{ index, inputState ->
                if (index == 0) return@map inputState.copy(value = hours)
                if (index == 1) return@map inputState.copy(
                    value = minutes,
                    wipeOnInput = true
                )
                if (index == 2) return@map inputState.copy(value = seconds)
                inputState
            }
        }
    }

    private fun calculateCarryValues(values: List<InputState>): List<InputState> {
        var carryValue: Carry? = null
        val updatedValues = values.reversed().mapIndexed map@{index, it ->
            // Allow the hours to be above 60
            if (it.value >= 60 && index != values.size - 1) {
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

    fun delete() {
        state.update { prevState ->
            prevState.map {
                if (it.inFocus) it.copy(value = 0) else it
            }

        }
    }

    fun reset() {
        state.update { currentState ->
            currentState.mapIndexed { index, inputState ->
                if (index == 1) {
                    inputState.copy(value = 10)
                } else
                    inputState.copy(value = 0)
            }
        }
    }

    fun removeFocus() {
        state.update { currentState ->
            currentState.map { it.copy(inFocus = false) }
        }
    }
}