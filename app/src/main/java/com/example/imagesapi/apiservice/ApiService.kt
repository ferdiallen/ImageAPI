package com.example.imagesapi.apiservice

import com.example.imagesapi.data.Url


interface ApiService {
    suspend fun getResponseAsStart(): Url
}