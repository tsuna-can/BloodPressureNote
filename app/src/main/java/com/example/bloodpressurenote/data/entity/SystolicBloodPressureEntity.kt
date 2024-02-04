package com.example.bloodpressurenote.data.entity

import androidx.room.ColumnInfo

data class SystolicBloodPressureEntity(
    @ColumnInfo(name = "systolicBloodPressure") val systolicBloodPressure: Int = 0,
)
