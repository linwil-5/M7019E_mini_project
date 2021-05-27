package com.example.test.network

import com.example.test.model.Downdetector
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass


@JsonClass(generateAdapter = true)
data class NetworkDowndetectorContainer(
    @Json(name = "downdetector")
    val detects: List<Downdetector> = listOf()
)

@JsonClass(generateAdapter = true)
data class NetworkDowndetector(
    @Json(name = "name")
    var name: String,

    @Json(name = "reachable")
    var reachable: Boolean
)

fun NetworkDowndetectorContainer.asDatabaseModel(): List<Downdetector> {
    return detects.map {
        Downdetector(
            name = it.name,
            reachable = it.reachable
        )
    }
}