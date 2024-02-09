package com.example.bloodpressurenote.model

data class StatisticsData(
    val averageSystolicBloodPressure: Double = 0.0,
    val averageDiastolicBloodPressure: Double = 0.0,
    val averageHeartRate: Double = 0.0,
    val systolicBloodPressureList: List<Int> = emptyList(),
    val diastolicBloodPressureList: List<Int> = emptyList(),
)
