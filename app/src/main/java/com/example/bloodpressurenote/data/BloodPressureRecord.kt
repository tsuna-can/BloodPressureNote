package com.example.bloodpressurenote.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.Date

@Entity(tableName = "blood_pressure_records")
data class BloodPressureRecord(
        @PrimaryKey(autoGenerate = true)
        val id: Int = 0,

        @ColumnInfo(name = "systolic_blood_pressure")
        val systolicBloodPressure: Int,

        @ColumnInfo(name = "diastolic_blood_pressure")
        val diastolicBloodPressure: Int,

        @ColumnInfo(name = "heart_rate")
        val heartRate: Int,

        val note: String,

        @ColumnInfo(name = "created_at")
        val createdAt: Date = Date()
)
