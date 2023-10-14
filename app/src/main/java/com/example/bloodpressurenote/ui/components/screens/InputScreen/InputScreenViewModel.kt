package com.example.bloodpressurenote.ui.components.screens.InputScreen

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.bloodpressurenote.data.BloodPressureRecord
import com.example.bloodpressurenote.data.BloodPressureRecordsRepository
import kotlinx.coroutines.launch

class InputScreenViewModel(private val bloodPressureRecordsRepository: BloodPressureRecordsRepository) :
    ViewModel() {

    var inputUiState by mutableStateOf(InputUiState())
        private set

    fun updateSystolicBloodPressure(value: String) {
        inputUiState =
            inputUiState.copy(
                bloodPressureDetails = inputUiState.bloodPressureDetails.copy(systolicBloodPressure = value),
                isSystolicBloodPressureValid = validateBloodPressure(value)
            )
    }

    fun updateDiastolicBloodPressure(value: String) {
        inputUiState =
            inputUiState.copy(
                bloodPressureDetails = inputUiState.bloodPressureDetails.copy(diastolicBloodPressure = value),
                isDiastolicBloodPressureValid = validateBloodPressure(value)
            )
    }

    fun updateHeartRate(value: String) {
        inputUiState =
            inputUiState.copy(
                bloodPressureDetails = inputUiState.bloodPressureDetails.copy(heartRate = value),
                isHeartRateValid = when {
                    value.length > 3 -> ErrorType.MORE_THAN_3_DIGITS
                    value.isNotBlank() && value.toIntOrNull() == null -> ErrorType.NOT_NUMERIC
                    else -> null
                }
            )
    }

    fun updateNote(value: String) {
        inputUiState =
            inputUiState.copy(
                bloodPressureDetails = inputUiState.bloodPressureDetails.copy(note = value),
                isNoteValid = if (value.length > 100) ErrorType.MORE_THAN_100_DIGITS else null
            )
    }

    private fun validateBloodPressure(value: String): ErrorType? {
        return when {
            value.isBlank() -> ErrorType.IS_BLANK
            value.length > 3 -> ErrorType.MORE_THAN_3_DIGITS
            value.isNotBlank() && value.toIntOrNull() == null -> ErrorType.NOT_NUMERIC
            else -> null
        }
    }

    fun saveItem() {
        viewModelScope.launch {
            bloodPressureRecordsRepository.insertItem(
                inputUiState.bloodPressureDetails.toBloodPressureRecord()
            )
        }
    }

}

data class InputUiState(
    val bloodPressureDetails: BloodPressureDetails = BloodPressureDetails(),
    val isSystolicBloodPressureValid: ErrorType? = null,
    val isDiastolicBloodPressureValid: ErrorType? = null,
    val isHeartRateValid: ErrorType? = null,
    val isNoteValid: ErrorType? = null,
    val enableSave: Boolean = false
)

data class BloodPressureDetails(
    val id: Int = 0,
    val systolicBloodPressure: String = "",
    val diastolicBloodPressure: String = "",
    val heartRate: String = "",
    val note: String = "",
)

enum class ErrorType {
    MORE_THAN_3_DIGITS, MORE_THAN_100_DIGITS, IS_BLANK, NOT_NUMERIC
}

fun BloodPressureDetails.toBloodPressureRecord(): BloodPressureRecord = BloodPressureRecord(
    id = id,
    systolicBloodPressure = systolicBloodPressure.toIntOrNull() ?: 0,
    diastolicBloodPressure = diastolicBloodPressure.toIntOrNull() ?: 0,
    heartRate = heartRate.toIntOrNull() ?: 0,
    note = note
)
