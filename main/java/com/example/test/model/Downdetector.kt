package com.example.test.model


import android.os.Parcelable
import com.squareup.moshi.Json
import kotlinx.parcelize.Parcelize

@Parcelize
data class Downdetector(
    val name: String,
    val reachable: Boolean
) : Parcelable