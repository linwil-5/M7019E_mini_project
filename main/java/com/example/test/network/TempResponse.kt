package com.example.test.network

import com.example.test.model.Temp
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass


@JsonClass(generateAdapter = true)
data class NetworkTempContainer(
    @Json(name = "temps")
    val temps: List<Temp> = listOf()
)

@JsonClass(generateAdapter = true)
data class NetworkTemp(
    @Json(name = "name")
    var name: String,

    @Json(name = "temp")
    var temp: String
)

fun NetworkTempContainer.asDatabaseModel(): List<Temp> {
    return temps.map {
        Temp(
            name = it.name,
            temp = it.temp
        )
    }
}