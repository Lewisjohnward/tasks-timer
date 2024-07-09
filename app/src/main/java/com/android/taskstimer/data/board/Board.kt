package com.android.taskstimer.data.board

import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "boards")
data class Board(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0
)
