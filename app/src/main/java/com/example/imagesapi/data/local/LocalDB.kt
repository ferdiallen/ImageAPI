package com.example.imagesapi.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class LocalDB(
    val artist_href: String,
    val artist_name: String,
    val source_url: String,
    val url: String,
    @PrimaryKey val id: Int? = null
)
