package com.helloanwar.hellomvvm.data.source.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.helloanwar.hellomvvm.data.model.PhotoResponseItem

@Database(entities = [PhotoResponseItem::class], version = 1)
abstract class HelloMVVMDatabase : RoomDatabase() {
    abstract fun photoDao(): PhotoItemDao
}