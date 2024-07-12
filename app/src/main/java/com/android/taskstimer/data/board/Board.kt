package com.android.taskstimer.data.board

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.Relation
import com.android.taskstimer.data.timer.Timer


@Entity(tableName = "boards")
data class Board(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val name: String,
)

data class BoardWithTimers(
    @Embedded val timer: Timer,
)
