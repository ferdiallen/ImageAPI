package com.example.imagesapi.localremote

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy.REPLACE
import androidx.room.Query
import com.example.imagesapi.data.local.LocalDB
import kotlinx.coroutines.flow.Flow

@Dao
interface DatabaseDAO {
    @Query("SELECT* FROM LocalDB")
    suspend fun getAllData(): List<LocalDB>

    @Insert(onConflict = REPLACE)
    suspend fun insertNewData(data: LocalDB)

    @Delete
    suspend fun deleteData(data: LocalDB)

    @Query("DELETE FROM LocalDB")
    suspend fun deleteAll()
}