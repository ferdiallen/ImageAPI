package com.example.imagesapi.module

import android.content.Context
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.imagesapi.apiservice.ApiImpl
import com.example.imagesapi.apiservice.ApiService
import com.example.imagesapi.data.local.LocalDB
import com.example.imagesapi.repo.DatabaseLocal
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import io.ktor.client.*
import io.ktor.client.engine.android.*
import io.ktor.client.features.json.*
import io.ktor.client.features.json.serializer.*
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModules {
    @Singleton
    @Provides
    fun create(): ApiService {
        return ApiImpl(httpInject = HttpClient(Android) {
            install(JsonFeature) {
                serializer = KotlinxSerializer()
            }
        }
        )
    }

    @Provides
    @Singleton
    fun createDB(@ApplicationContext context: Context) =
        Room.databaseBuilder(context, DatabaseLocal::class.java, "db_anime").build()

    @Provides
    @Singleton
    fun providesDao(db:DatabaseLocal) = db.getDao()

}