package com.example.bloodpressurenote.domain

import com.example.bloodpressurenote.data.BloodPressureRecordsRepository
import com.example.bloodpressurenote.model.StatisticsData
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import javax.inject.Inject

class GetStatisticsDataUseCase @Inject constructor(
    private val bloodPressureRecordsRepository: BloodPressureRecordsRepository,
) {
    operator fun invoke(): Flow<StatisticsData> = combine(
        bloodPressureRecordsRepository.getAverageRecord(),
        bloodPressureRecordsRepository.getAllBloodPressure(),
    ) {
            averageRecord, bloodPressure ->
        StatisticsData(
            averageSystolicBloodPressure = averageRecord.systolicBloodPressure,
            averageDiastolicBloodPressure = averageRecord.diastolicBloodPressure,
            averageHeartRate = averageRecord.heartRate,
            systolicBloodPressureList = bloodPressure.first,
            diastolicBloodPressureList = bloodPressure.second,
        )
    }
}
