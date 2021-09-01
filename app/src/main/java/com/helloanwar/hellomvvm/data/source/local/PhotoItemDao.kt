package com.helloanwar.hellomvvm.data.source.local

import androidx.room.*
import com.helloanwar.hellomvvm.data.model.PhotoResponseItem

@Dao
interface PhotoItemDao {
    @Query("SELECT * FROM photos")
    suspend fun getAll(): List<PhotoResponseItem>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(vararg photos: PhotoResponseItem)

    @Delete
    suspend fun delete(photo: PhotoResponseItem)

    @Query("DELETE FROM photos")
    suspend fun deleteAll()

}