package com.example.imagesapi.data

import com.example.imagesapi.data.local.LocalDB
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Url(
    @SerialName("url") val urls: List<insideData>
) {
    @Serializable
    data class insideData(
        val artist_href: String,
        val artist_name: String,
        val source_url: String,
        val url: String
    ) {
        fun toLocal(): LocalDB {
            return LocalDB(
                artist_href, artist_name, source_url, url
            )
        }
    }
}