package com.example.test.viewmodel

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.test.database.ServerDatabaseDao
import java.lang.IllegalArgumentException

class HomeScreenViewModelFactory(
    private val serverDatabaseDao: ServerDatabaseDao,
    private val application: Application
) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(HomeScreenViewModel::class.java)) {
            return HomeScreenViewModel(serverDatabaseDao, application) as T
        }
        throw IllegalArgumentException("At HomeScreen. Some unknown ViewModel class")
    }
}