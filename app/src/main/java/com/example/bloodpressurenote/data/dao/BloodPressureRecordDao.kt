package com.example.bloodpressurenote.data.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.bloodpressurenote.data.BloodPressureRecord
import com.example.bloodpressurenote.data.entity.AverageEntity
import com.example.bloodpressurenote.data.entity.DiastolicBloodPressureEntity
import com.example.bloodpressurenote.data.entity.SystolicBloodPressureEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface BloodPressureRecordDao {

    @Query("SELECT * FROM blood_pressure_records ORDER BY id ASC")
    fun getAllItems(): Flow<List<BloodPressureRecord>>

    @Query("SELECT * FROM blood_pressure_records WHERE id = :id")
    fun getItem(id: Int): Flow<BloodPressureRecord>

    // Specify the conflict strategy as IGNORE, when the user tries to add an
    // existing Item into the database Room ignores the conflict.
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(bloodPressureRecord: BloodPressureRecord)

    @Update
    suspend fun update(bloodPressureRecord: BloodPressureRecord)

    @Delete
    suspend fun delete(bloodPressureRecord: BloodPressureRecord)

    @Query(
        "SELECT systolic_blood_pressure as systolicBloodPressure FROM blood_pressure_records ORDER BY created_at ASC",
    )
    fun getAllSystolicBloodPressure(): Flow<List<SystolicBloodPressureEntity>>

    @Query(
        "SELECT diastolic_blood_pressure as diastolicBloodPressure FROM blood_pressure_records ORDER BY created_at ASC",
    )
    fun getAllDiastolicBloodPressure(): Flow<List<DiastolicBloodPressureEntity>>

    @Query(
        "SELECT AVG(systolic_blood_pressure) as systolicBloodPressure, AVG(diastolic_blood_pressure) as diastolicBloodPressure, AVG(heart_rate) as heartRate FROM blood_pressure_records",
    )
    fun getAverageRecord(): Flow<AverageEntity>
}
