package com.example.test.viewmodel

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import java.lang.IllegalArgumentException

class TempHistoryViewModelFactory(
    private val application: Application
) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(TempHistoryViewModel::class.java)) {
            return TempHistoryViewModel(application) as T
        }
        throw IllegalArgumentException("unknown temphistory ViewModel class")
    }
}