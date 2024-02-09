package com.example.bloodpressurenote.data.entity

import androidx.room.ColumnInfo

data class AverageEntity(
    @ColumnInfo(name = "systolicBloodPressure") val systolicBloodPressure: Double = 0.0,
    @ColumnInfo(name = "diastolicBloodPressure") val diastolicBloodPressure: Double = 0.0,
    @ColumnInfo(name = "heartRate") val heartRate: Double = 0.0,
)
