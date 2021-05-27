package com.example.test.viewmodel

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.test.database.ServerDatabase
import com.example.test.database.ServerDatabaseDao
import com.example.test.model.*
import com.example.test.repository.ServerRepository
import kotlinx.coroutines.launch

class HomeScreenViewModel(
    private val serverDatabaseDao: ServerDatabaseDao,
    application: Application
) : AndroidViewModel(application) {

    private val TAG = "HomeScreenViewModel"

    private val serverRepository = ServerRepository(ServerDatabase.getInstance(application))

    val tempList = serverRepository.tempServers

    private val _navigateToTempDetail = MutableLiveData<Temp>()
    val navigateToTempDetail: LiveData<Temp>
        get() {
            return _navigateToTempDetail
        }

    init {

    }

    fun getTemperaturesServers() {
        viewModelScope.launch {
            try {
                serverRepository.getTemperatures()
            } catch (e: Exception) {
                Log.d(TAG, "Something failed")
                Log.d(TAG, "Templist2 is " + tempList.value)
            }
        }
    }

    fun onTempListItemClicked(temp: Temp) {
        Log.d(TAG, "onTempListItemClicked: Value for temp is " + temp)
        _navigateToTempDetail.value = temp
    }

    fun onTempDetailNavigated() {
        _navigateToTempDetail.value = null
    }

}