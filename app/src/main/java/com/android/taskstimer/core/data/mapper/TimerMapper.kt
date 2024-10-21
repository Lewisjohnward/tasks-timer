package com.android.taskstimer.core.data.mapper

import com.android.taskstimer.core.data.local.board.BoardEntity
import com.android.taskstimer.core.data.local.timer.TimerEntity
import com.android.taskstimer.core.domain.model.BoardItem
import com.android.taskstimer.core.domain.model.TimerItem

fun TimerItem.toTimerEntity(): TimerEntity{
    return TimerEntity(
        id = id,
        boardId = boardId,
        name = name,
        presetTime = presetTime,
        remainingTime = remainingTime,
        lastEndedAt = lastEndedAt
    )
}

fun TimerEntity.toTimerItem(): TimerItem{
    return TimerItem(
        id = id,
        boardId= boardId,
        name = name,
        presetTime = presetTime,
        remainingTime = remainingTime,
        lastEndedAt = lastEndedAt
    )
}