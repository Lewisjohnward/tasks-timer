package com.android.taskstimer.core.data.local.board

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.Relation
import com.android.taskstimer.core.data.local.timer.TimerEntity
import com.android.taskstimer.core.domain.model.BoardItem


@Entity(tableName = "boards")
data class BoardEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val name: String,
)

data class BoardsWithTimersEntity(
    @Embedded val board: BoardItem,
    @Relation(
        parentColumn = "id",
        entityColumn = "board_id"
    )
    val timers: List<TimerEntity>
)

