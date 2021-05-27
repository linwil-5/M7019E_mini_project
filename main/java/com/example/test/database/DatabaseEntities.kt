package com.example.test.database

import com.example.test.model.Temp

fun List<Temp>.asDomainPopularModel(): List<Temp> {
    return map {
        Temp(
            name = it.name,
            temp = it.temp
        )
    }
}