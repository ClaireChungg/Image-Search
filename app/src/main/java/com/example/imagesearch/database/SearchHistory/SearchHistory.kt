package com.example.imagesearch.database.SearchHistory

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class SearchHistory(
    @PrimaryKey(autoGenerate = true) var id: Int,
    @ColumnInfo(name = "query_text") val queryText: String,
    @ColumnInfo(name = "update_time") val updateTime: Long = System.currentTimeMillis()
)