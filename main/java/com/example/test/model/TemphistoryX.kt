package com.example.test.model


import com.squareup.moshi.Json

data class TemphistoryX(
    val name: String,
    val temps: List<TempX>
)