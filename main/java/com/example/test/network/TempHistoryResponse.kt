package com.example.test.network

import com.example.test.model.TemphistoryX

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass


@JsonClass(generateAdapter = true)
data class NetworkTempHistoryContainer(

    @Json(name = "temphistory")
    val temphistory: List<TemphistoryX>
)
