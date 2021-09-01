package com.helloanwar.hellomvvm.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "photos")
data class PhotoResponseItem(
    val albumId: Int,
    @PrimaryKey val id: Int,
    val thumbnailUrl: String,
    val title: String,
    val url: String
)