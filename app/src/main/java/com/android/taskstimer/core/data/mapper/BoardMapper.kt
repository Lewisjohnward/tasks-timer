package com.android.taskstimer.core.data.mapper

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.Relation
import com.android.taskstimer.core.data.local.board.BoardEntity
import com.android.taskstimer.core.data.local.board.BoardsWithTimersEntity
import com.android.taskstimer.core.data.local.timer.TimerEntity
import com.android.taskstimer.core.domain.model.BoardItem
import com.android.taskstimer.core.domain.model.BoardsWithTimersItem
import com.android.taskstimer.core.domain.model.TimerItem


fun BoardItem.toBoardEntityForInsert(): BoardEntity {
    return BoardEntity(
        name = name
    )
}

fun BoardEntity.toBoardItem(): BoardItem {
    return BoardItem(
        name = name,
        id = id ?: 0
    )
}

fun BoardsWithTimersEntity.toBoardsWithTimersItem(): BoardsWithTimersItem {
    return BoardsWithTimersItem(
        board = board,
        timers = timers.map { it.toTimerItem() }
    )
}