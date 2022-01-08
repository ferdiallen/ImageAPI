package com.example.imagesapi.repo

import com.example.imagesapi.data.local.LocalDB
import com.example.imagesapi.localremote.DatabaseDAO
import javax.inject.Inject

class RepoDatabase @Inject constructor(
    private val dao: DatabaseDAO
) {
    suspend fun getAllData() = dao.getAllData()
    suspend fun addData(data: LocalDB) = dao.insertNewData(data)
    suspend fun deleteData(data: LocalDB) = dao.deleteData(data)
    suspend fun deleteAllData() = dao.deleteAll()
}