package com.example.test.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.test.database.Helps
import com.example.test.model.Help

class HelpViewModel(application: Application) : AndroidViewModel(application) {

    private val _results = MutableLiveData<List<Help>>()
    val results: LiveData<List<Help>>
        get() {
            return _results
        }

    init {

    }

    fun getHelpList() {
        _results.value = Helps().list
    }


}