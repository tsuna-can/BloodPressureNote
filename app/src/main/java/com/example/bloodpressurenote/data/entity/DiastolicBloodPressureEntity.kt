package com.example.bloodpressurenote.data.entity

import androidx.room.ColumnInfo

data class DiastolicBloodPressureEntity(
    @ColumnInfo(name = "diastolicBloodPressure") val diastolicBloodPressure: Int = 0,
)
