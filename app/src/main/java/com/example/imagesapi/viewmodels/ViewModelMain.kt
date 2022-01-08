package com.example.imagesapi.viewmodels

import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.imagesapi.apiservice.ApiService
import com.example.imagesapi.data.local.LocalDB
import com.example.imagesapi.repo.RepoDatabase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ViewModelMain @Inject constructor(
    private val clients: ApiService,
    private val db: RepoDatabase
) : ViewModel() {
    val myData = mutableStateOf<List<LocalDB>>(listOf())
    private val _searchText = mutableStateOf("")
    val searchText: State<String> = _searchText

    init {
        getRandomData()
    }

    fun getRandomData() {
        viewModelScope.launch {
            val result = clients.getResponseAsStart()
            result.urls.let {
                if (db.getAllData().isNotEmpty()) db.deleteAllData()
                it.forEach { out ->
                    db.addData(out.toLocal())
                }
            }
            myData.value = db.getAllData()
        }
    }

    fun searchText(text: String) {
        _searchText.value = text
    }
}