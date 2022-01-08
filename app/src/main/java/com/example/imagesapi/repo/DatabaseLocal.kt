package com.example.imagesapi.repo

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.imagesapi.data.local.LocalDB
import com.example.imagesapi.localremote.DatabaseDAO

@Database(entities = [LocalDB::class], version = 1)
abstract class DatabaseLocal : RoomDatabase() {
    abstract fun getDao(): DatabaseDAO
}