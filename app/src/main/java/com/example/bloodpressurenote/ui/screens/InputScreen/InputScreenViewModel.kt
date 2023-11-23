package com.example.bloodpressurenote.ui.screens.InputScreen

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.bloodpressurenote.data.BloodPressureRecord
import com.example.bloodpressurenote.data.BloodPressureRecordsRepository
import com.example.bloodpressurenote.util.StringResource
import com.example.bloodpressurenote.util.validator
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import java.time.LocalDateTime
import java.time.ZoneOffset
import java.util.Date
import javax.inject.Inject

@HiltViewModel
class InputScreenViewModel @Inject constructor(
    private val bloodPressureRecordsRepository: BloodPressureRecordsRepository,
) : ViewModel() {

    var inputUiState by mutableStateOf(InputUiState())
        private set

    fun updateBloodPressure(value: String, bloodPressureType: String) {
        val validationResult =
            validator(value = value, maxLength = 3, isNumeric = true)
        inputUiState =
            when (bloodPressureType) {
                "systolic" -> {
                    inputUiState.copy(
                        bloodPressureDetails = inputUiState.bloodPressureDetails.copy(
                            systolicBloodPressure = value,
                        ),
                        systolicBPErrorMessage = validationResult,
                    )
                }

                "diastolic" -> {
                    inputUiState.copy(
                        bloodPressureDetails = inputUiState.bloodPressureDetails.copy(
                            diastolicBloodPressure = value,
                        ),
                        diastolicBPErrorMessage = validationResult,
                    )
                }

                else -> inputUiState
            }
    }

    fun updateHeartRate(value: String) {
        inputUiState =
            inputUiState.copy(
                bloodPressureDetails = inputUiState.bloodPressureDetails.copy(heartRate = value),
                heartRateErrorMessage = validator(
                    value = value,
                    maxLength = 3,
                    allowBlank = true,
                    isNumeric = true,
                ),
            )
    }

    fun updateNote(value: String) {
        inputUiState =
            inputUiState.copy(
                bloodPressureDetails = inputUiState.bloodPressureDetails.copy(note = value),
                noteErrorMessage = validator(value = value, maxLength = 100, allowBlank = true),
            )
    }

    fun updateDate(value: Long?) {
        inputUiState =
            inputUiState.copy(
                bloodPressureDetails = inputUiState.bloodPressureDetails.copy(
                    date = value ?: Date().time,
                ),
            )
    }

    fun saveItem() {
        viewModelScope.launch {
            bloodPressureRecordsRepository.insertItem(
                inputUiState.bloodPressureDetails.toBloodPressureRecord(),
            )
            refreshViewModel()
        }
    }

    private fun refreshViewModel() {
        inputUiState = InputUiState()
    }
}

data class InputUiState(
    val bloodPressureDetails: BloodPressureDetails = BloodPressureDetails(),
    val systolicBPErrorMessage: StringResource? = null,
    val diastolicBPErrorMessage: StringResource? = null,
    val heartRateErrorMessage: StringResource? = null,
    val noteErrorMessage: StringResource? = null,
    val enableSave: Boolean = false,
)

data class BloodPressureDetails(
    val id: Int = 0,
    val systolicBloodPressure: String = "",
    val diastolicBloodPressure: String = "",
    val heartRate: String = "",
    val note: String = "",
    val date: Long = LocalDateTime.now().toEpochSecond(ZoneOffset.UTC).times(1000),
)

fun BloodPressureDetails.toBloodPressureRecord(): BloodPressureRecord = BloodPressureRecord(
    id = id,
    systolicBloodPressure = systolicBloodPressure.toIntOrNull() ?: 0,
    diastolicBloodPressure = diastolicBloodPressure.toIntOrNull() ?: 0,
    heartRate = heartRate.toIntOrNull() ?: 0,
    note = note,
    createdAt = Date(date),
)
