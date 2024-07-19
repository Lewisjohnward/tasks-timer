package com.android.taskstimer.core.data.board

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.Relation
import com.android.taskstimer.core.data.timer.Timer


@Entity(tableName = "boards")
data class Board(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val name: String,
)

data class BoardsWithTimers(
    @Embedded val board: Board,
    @Relation(
        parentColumn = "id",
        entityColumn = "board_id"
    )
    val timers: List<Timer>
)
//@Relation(
//    parentColumn = "name",
//    entityColumn = "board"
//)
