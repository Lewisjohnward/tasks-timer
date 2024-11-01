package com.android.taskstimer.core.data.local.board

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.android.taskstimer.core.presentation.ui.IconKey


@Entity(tableName = "boards")
data class BoardEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val name: String,
    val iconKey: IconKey,
    val selected: Boolean = false
)