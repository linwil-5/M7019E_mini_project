package com.example.test.repository

import androidx.lifecycle.LiveData
import com.example.test.database.ServerDatabase
import com.example.test.model.Downdetector
import com.example.test.model.Post
import com.example.test.model.Temp
import com.example.test.model.TemphistoryX
import com.example.test.network.asDatabaseModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.Response

class ServerRepository(private val database: ServerDatabase) {

    val tempServers: LiveData<List<Temp>> = database.serverDatabaseDao.getLiveTemps()


    suspend fun getServers(): List<Downdetector> {
        return TMDBApi.SERVER_LIST_RETROFIT_SERVICE.getDowndetector().asDatabaseModel()
    }

    suspend fun getTemperatures() {
        withContext(Dispatchers.IO) {
            val tempList = TMDBApi.SERVER_LIST_RETROFIT_SERVICE.getTemp()
            database.serverDatabaseDao.insertAllTemps(tempList.asDatabaseModel())
        }
    }

    suspend fun pushPost(post: Post): Response<Post> {
        return TMDBApi.SERVER_LIST_RETROFIT_SERVICE.startServer(post)
    }

    suspend fun getTempHistory(): List<TemphistoryX> {
        return TMDBApi.SERVER_LIST_RETROFIT_SERVICE.getTempHistory().temphistory
    }

}