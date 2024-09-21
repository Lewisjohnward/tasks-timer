package com.android.taskstimer.core.data.mapper

import com.android.taskstimer.core.data.local.board.BoardEntity
import com.android.taskstimer.core.domain.model.BoardItem


fun BoardItem.toBoardEntityForInsert(): BoardEntity {
    return BoardEntity(
        name = name,
        iconKey = iconKey
    )
}

fun BoardItem.toBoardEntityForDelete(): BoardEntity {
    return BoardEntity(
        name = name,
        id = id,
        iconKey = iconKey
    )
}

fun BoardEntity.toBoardItem(): BoardItem {
    return BoardItem(
        name = name,
        id = id,
        iconKey = iconKey
    )
}