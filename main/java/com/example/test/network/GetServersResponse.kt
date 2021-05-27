package com.example.test.network

import com.example.test.model.Server

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass


@JsonClass(generateAdapter = true)
data class NetworkGetServersContainer(
    val servers: List<Server> = listOf()
)

@JsonClass(generateAdapter = true)
data class NetworkGetServers(
    @Json(name = "name")
    var name: String,

    @Json(name = "ip")
    var ip: String
)

fun NetworkGetServersContainer.asDatabaseModel(): List<Server> {
    return servers.map {
        Server(
            name = it.name,
            ip = it.ip
        )
    }
}