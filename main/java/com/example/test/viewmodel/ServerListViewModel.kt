package com.example.test.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.test.database.ServerDatabase
import com.example.test.model.Downdetector
import com.example.test.model.Post
import com.example.test.network.DataFetchStatus
import kotlinx.coroutines.launch
import java.lang.Exception
import com.example.test.repository.ServerRepository
import retrofit2.Response

class ServerListViewModel(application: Application) : AndroidViewModel(application) {

    private val TAG = "ServerListViewModel"
    private val serverRepository = ServerRepository(ServerDatabase.getInstance(application))

    var myResponse: MutableLiveData<Response<Post>> = MutableLiveData()

    private val _dataFetchStatus = MutableLiveData<DataFetchStatus>()
    val dataFetchStatus: LiveData<DataFetchStatus>
        get() {
            return _dataFetchStatus
        }

    private val _serverList = MutableLiveData<List<Downdetector>>()
    val serverList: LiveData<List<Downdetector>>
        get() {
            return _serverList
        }


    init {

    }

    fun pushPost(post: Post) {
        viewModelScope.launch {
            val response: Response<Post> = serverRepository.pushPost(post)
            myResponse.value = response
        }
    }

    fun getServersDownDetectionList() {
        viewModelScope.launch {
            try {
                _serverList.value = serverRepository.getServers()
                _dataFetchStatus.value = DataFetchStatus.DONE
            } catch (e: Exception) {
                _dataFetchStatus.value = DataFetchStatus.ERROR
            }
        }
    }

    fun onServerListItemClicked(downDetector: Downdetector) {
        viewModelScope.launch {
            val myPost = Post(downDetector.name)
            pushPost(myPost)
        }
    }


}