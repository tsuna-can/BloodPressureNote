package com.example.bloodpressurenote.ui.screens.statistics

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.bloodpressurenote.data.BloodPressureRecordsRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

@HiltViewModel
class StatisticsScreenViewModel @Inject constructor(
    private val bloodPressureRecordsRepository: BloodPressureRecordsRepository,
) : ViewModel() {

    val statisticsUiState: StateFlow<StatisticsUiState> =
        bloodPressureRecordsRepository.getAverageRecord()
            .map {
                StatisticsUiState(
                    systolicBloodPressure = it.systolicBloodPressure,
                    diastolicBloodPressure = it.diastolicBloodPressure,
                    heartRate = it.heartRate,
                )
            }
            .stateIn(
                viewModelScope,
                started = SharingStarted.WhileSubscribed(5_000L),
                StatisticsUiState(),
            )
}

data class StatisticsUiState(
    val systolicBloodPressure: Double = 0.0,
    val diastolicBloodPressure: Double = 0.0,
    val heartRate: Double = 0.0,
)
