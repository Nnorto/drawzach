package com.example.drawzach

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface DrawingDao {
    @Insert
    suspend fun insert(drawing: Drawing)

    @Query("SELECT * FROM drawings")
    suspend fun getAll(): List<Drawing>
}
