package com.android.taskstimer.core.data.mapper

import com.android.taskstimer.core.data.local.board.BoardEntity
import com.android.taskstimer.core.domain.model.BoardItem


fun BoardItem.toBoardEntity(): BoardEntity {
    return BoardEntity(
        id = id,
        name = name,
        iconKey = iconKey,
        selected = selected,
        timerCount = timerCount,
        totalSeconds = totalSeconds
    )
}

fun BoardEntity.toBoardItem(): BoardItem {
    return BoardItem(
        name = name,
        id = id,
        iconKey = iconKey,
        selected = selected,
        timerCount = timerCount,
        totalSeconds = totalSeconds
    )
}