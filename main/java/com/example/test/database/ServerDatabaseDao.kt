package com.example.test.database

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.test.model.Temp

@Dao
interface ServerDatabaseDao {

    @Insert
    suspend fun insertTemp(temp: Temp)

    @Delete
    suspend fun deleteTemp(temp: Temp)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAllTemps(populars: List<Temp>)

    @Query("SELECT * FROM temperatures")
    fun getLiveTemps(): LiveData<List<Temp>>

    @Query("SELECT * FROM temperatures ORDER BY `temp` ASC")
    suspend fun getAllTemps(): List<Temp>

}