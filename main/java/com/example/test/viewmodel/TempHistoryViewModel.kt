package com.example.test.viewmodel

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.test.database.ServerDatabase
import com.example.test.model.TempX
import com.example.test.network.DataFetchStatus
import com.example.test.repository.ServerRepository
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat

class TempHistoryViewModel(application: Application) : AndroidViewModel(application) {

    private val TAG = "TempHistoryViewModel"

    private val serverRepository = ServerRepository(ServerDatabase.getInstance(application))

    private val _dataFetchStatus = MutableLiveData<DataFetchStatus>()
    val dataFetchStatus: LiveData<DataFetchStatus>
        get() {
            return _dataFetchStatus
        }

    private val _tempHistoryList = MutableLiveData<List<TempX>>()
    val tempHistoryList: LiveData<List<TempX>>
        get() {
            return _tempHistoryList
        }

    init {

    }

    fun getTempHistory(nameServer: String) {
        viewModelScope.launch {
            try {
                Log.d(TAG, "getTempHistory: Server name " + nameServer)
                serverRepository.getTempHistory().forEach() {
                    if (nameServer == it.name) {
                        val format = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss")
                        val format2 = SimpleDateFormat("hh:mm aa")

                        val date = format.parse(it.temps[0].time)
                        val date2 = format2.format(date)
                        Log.d(TAG, "getTempHistory: " + date2)
                        _tempHistoryList.value = it.temps
                    }
                }
                _dataFetchStatus.value = DataFetchStatus.DONE
            } catch (e: Exception) {
                _dataFetchStatus.value = DataFetchStatus.ERROR
            }
        }
    }
}