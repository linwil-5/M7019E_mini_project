package com.example.test.model


import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.squareup.moshi.Json
import kotlinx.parcelize.Parcelize

@Parcelize
@Entity(tableName = "temperatures")
data class Temp(

    @PrimaryKey(autoGenerate = false) // Not auto-generated since we are giving each an id value
    val name: String,

    @ColumnInfo(name = "temp")
    val temp: String
) : Parcelable