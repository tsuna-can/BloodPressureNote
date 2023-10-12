package com.example.bloodpressurenote.data

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

@Dao
interface BloodPressureRecordDao {

    @Query("SELECT * from blood_pressure_records ORDER BY id ASC")
    fun getAllItems(): Flow<List<BloodPressureRecord>>

    @Query("SELECT * from blood_pressure_records WHERE id = :id")
    fun getItem(id: Int): Flow<BloodPressureRecord>

    // Specify the conflict strategy as IGNORE, when the user tries to add an
    // existing Item into the database Room ignores the conflict.
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(bloodPressureRecord: BloodPressureRecord)

    @Update
    suspend fun update(bloodPressureRecord: BloodPressureRecord)

    @Delete
    suspend fun delete(bloodPressureRecord: BloodPressureRecord)
}
