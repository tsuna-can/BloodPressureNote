package com.example.bloodpressurenote.ui.screens.statistics

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.bloodpressurenote.data.BloodPressureRecordsRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import java.text.DecimalFormat
import javax.inject.Inject

@HiltViewModel
class StatisticsScreenViewModel @Inject constructor(
    private val bloodPressureRecordsRepository: BloodPressureRecordsRepository,
) : ViewModel() {

    private val df = DecimalFormat("#.#")

    val statisticsUiState: StateFlow<StatisticsUiState> =
        bloodPressureRecordsRepository.getAverageRecord()
            .map {
                StatisticsUiState(
                    systolicBloodPressure = df.format(it.systolicBloodPressure).toString(),
                    diastolicBloodPressure = df.format(it.diastolicBloodPressure).toString(),
                    heartRate = df.format(it.heartRate).toString(),
                )
            }
            .stateIn(
                viewModelScope,
                started = SharingStarted.WhileSubscribed(5_000L),
                StatisticsUiState(),
            )
}

data class StatisticsUiState(
    val systolicBloodPressure: String = "",
    val diastolicBloodPressure: String = "",
    val heartRate: String = "",
)
