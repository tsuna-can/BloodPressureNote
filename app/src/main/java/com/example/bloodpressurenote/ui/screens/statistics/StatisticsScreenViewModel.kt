package com.example.bloodpressurenote.ui.screens.statistics

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.bloodpressurenote.domain.GetStatisticsDataUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import java.text.DecimalFormat
import javax.inject.Inject

@HiltViewModel
class StatisticsScreenViewModel @Inject constructor(
    private val getStatisticsDataUseCase: GetStatisticsDataUseCase,
) : ViewModel() {

    private val df = DecimalFormat("#.#")

    val statisticsUiState: StateFlow<StatisticsUiState> =
        getStatisticsDataUseCase().map {
            StatisticsUiState(
                systolicBloodPressure = df.format(it.averageSystolicBloodPressure),
                diastolicBloodPressure = df.format(it.averageDiastolicBloodPressure),
                heartRate = df.format(it.averageHeartRate),
                systolicBloodPressureList = it.systolicBloodPressureList,
                diastolicBloodPressureList = it.diastolicBloodPressureList,
            )
        }.stateIn(
            viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000L),
            StatisticsUiState(),
        )
}

data class StatisticsUiState(
    val systolicBloodPressure: String = "",
    val diastolicBloodPressure: String = "",
    val heartRate: String = "",
    val systolicBloodPressureList: List<Int> = emptyList(),
    val diastolicBloodPressureList: List<Int> = emptyList(),
)
