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