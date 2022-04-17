package com.mar.project.drinkingreminder.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "item")
data class Item (
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,

    @ColumnInfo(name = "time")
    val time: String,

    @ColumnInfo(name = "size")
    val size: Int = 0
)