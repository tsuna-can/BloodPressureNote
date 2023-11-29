package com.example.bloodpressurenote.ui.screens.calendar

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.bloodpressurenote.data.BloodPressureRecord
import com.example.bloodpressurenote.data.BloodPressureRecordsRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

@HiltViewModel
class CalendarScreenViewModel @Inject constructor(
    private val bloodPressureRecordsRepository: BloodPressureRecordsRepository,
) : ViewModel() {

    val homeUiState: StateFlow<CalendarUiState> =
        bloodPressureRecordsRepository.getAllItemsStream()
            .map { CalendarUiState(it) }
            .stateIn(
                viewModelScope,
                started = SharingStarted.WhileSubscribed(TIMEOUT_MILLIS),
                CalendarUiState(),
            )

    companion object {
        private const val TIMEOUT_MILLIS = 5_000L
    }
}

data class CalendarUiState(val recordList: List<BloodPressureRecord> = listOf())
