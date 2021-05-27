package com.example.test.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.test.utils.Constants.TEMP_LOCATION_COORDINATES
import kotlinx.coroutines.launch

class AboutViewModel(application: Application) : AndroidViewModel(application) {


    private val _detailLocation = MutableLiveData<String>()
    val detailLocation: LiveData<String>
        get() {
            return _detailLocation
        }

    init {

    }

    fun getLocation(location: String) {
        viewModelScope.launch {
            try {
                _detailLocation.value = TEMP_LOCATION_COORDINATES
            } catch (e: Exception) {
                _detailLocation.value = ""
            }
        }
    }


}