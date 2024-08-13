package com.android.taskstimer.core.data.mapper

import com.android.taskstimer.core.data.local.timer.TimerEntity
import com.android.taskstimer.core.domain.model.TimerItem

fun TimerItem.toTimerEntityForInsert(): TimerEntity{
    return TimerEntity(
        boardId = boardId,
        name = name,
        presetTime = presetTime,
        remainingTime = remainingTime
    )
}

fun TimerItem.toTimerEntityForUpdate(): TimerEntity{
    return TimerEntity(
        id = id,
        boardId = boardId,
        name = name,
        presetTime = presetTime,
        remainingTime = remainingTime
    )
}

fun TimerEntity.toTimerItem(): TimerItem{
    return TimerItem(
        id = id,
        boardId= boardId,
        name = name,
        presetTime = presetTime,
        remainingTime = remainingTime
    )
}