package com.example.bloodpressurenote.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "blood_pressure_records")
data class BloodPressureRecord(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val systolic_blood_pressure: Int,
    val diastolic_blood_pressure: Int,
    val heart_rate: Int,
    val note: String,
)
